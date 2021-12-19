
public class NoSuchTransactionException extends RuntimeException{
	public NoSuchTransactionException(String message) {
		super(message);
	}
	
	public NoSuchTransactionException() {
		super("No such transaction!");
	}
}
