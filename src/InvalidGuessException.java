/**
 * Ali Tejani amt3639
 * Sonal Jain sj23277
 * Thursday 9:30-11
 * Mastermind
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
