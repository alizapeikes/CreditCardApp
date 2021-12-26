
public class BankAccount {
	private String bankName;
	private String accountID;
	
	public BankAccount(String bankName, String accountID) {
		this.bankName = bankName;
		this.accountID = accountID;
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("Name: " + bankName + " AccountID: " + accountID);
		return str.toString();
	}
}
