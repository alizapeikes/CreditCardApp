import java.time.LocalDate;

public class Transaction {
	private long transcationID;
	private static long lastTransactionID = 0;
	private LocalDate transactionDate;
	private TransactionType transactionType;
	private double amount;
	
	public Transaction(LocalDate transactionDate, TransactionType transactionType, double amount) {
		lastTransactionID++;
		transcationID = lastTransactionID;
		this.transactionDate = transactionDate;
		this.transactionType = transactionType;
		this.amount = amount;
	}
	
	public Transaction(Transaction transaction) {
		this.transcationID = transaction.transcationID;
		this.transactionDate = transaction.transactionDate;
		this.transactionType = transaction.transactionType;
		this.amount = transaction.amount;
	}
	/**
	 * @return the transactionID
	 */
	public long getTransactionID() {
		return transcationID;
	}
	/**
	 * @param transactionID the transactionID to set
	 */
	public void setTransactionID(long transcationID) {
		this.transcationID = transcationID;
	}
	/**
	 * @return the lastTransactionID
	 */
	public long getLastTransactionID() {
		return lastTransactionID;
	}
	/**
	 * @param lastTransactionID the lastTransactionID to set
	 */
	public void setLastTransactionID(long lastTransactionID) {
		this.lastTransactionID = lastTransactionID;
	}
	/**
	 * @return the transactionDate
	 */
	public LocalDate getTransactionDate() {
		return transactionDate;
	}
	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}
	/**
	 * @return the transactionType
	 */
	public TransactionType getTransactionType() {
		return transactionType;
	}
	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Date: " + transactionDate + " Amount: " + amount);
		return str.toString();
	}
		
}
