
public class InactiveCardException extends RuntimeException {

	public InactiveCardException() {
		super("Card inactive.");
	}
	
	public InactiveCardException(String message) {
		super(message);
	}
}
