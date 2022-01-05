
public class NoSuchCardException extends RuntimeException {
	public NoSuchCardException(String message) {
		super(message);
	}
	
	public NoSuchCardException() {
		super("No such card exists.");
	}
}
