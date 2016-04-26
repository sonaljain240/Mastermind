/**
 * Created by Ali on 4/26/2016.
 */
public class InvalidGuessException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidGuessException(String message) {
		super(message);
	}

	public InvalidGuessException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
