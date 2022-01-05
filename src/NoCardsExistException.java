
public class NoCardsExistException extends RuntimeException {

	public NoCardsExistException() {
		super("No cards are currently being managed.");
	}
	
	public NoCardsExistException(String message) {
		super(message);
	}
}
