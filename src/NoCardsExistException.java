
public class NoCardsExistException extends RuntimeException {

	public NoCardsExistException() {
		super("No cards are being managed!");
	}
	
	public NoCardsExistException(String message) {
		super(message);
	}
}
