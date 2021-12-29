
public class CardExistsException extends RuntimeException {

	public CardExistsException() {
		super("Card exists!");
	}
	
	public CardExistsException(String message) {
		super(message);
	}
}
