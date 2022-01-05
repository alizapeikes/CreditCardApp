
public class CardExistsException extends RuntimeException {

	public CardExistsException() {
		super("Card already exists.");
	}
	
	public CardExistsException(String message) {
		super(message);
	}
}
