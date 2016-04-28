import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Ali on 4/25/2016.
 */
public class Game{
	private Code code;
	private int size;
	private LinkedList<Code> history;
	private Scanner keyboard;
	private Code key;
	private boolean testingMode;

	public Game(boolean testingModeOn) {
		history = new LinkedList<>();
		keyboard = new Scanner(System.in);
		testingMode = testingModeOn;
	}

	public void start() {
		System.out.print("\nWelcome to Mastermind. Here are the rules.\n\n" +
				  "This is a text version of the classic board game Mastermind.\n" +
				  "The computer will think of a secret code. The code consists of 4-8 colored pegs.\n" +
				  "The pegs may be one of six colors: blue, green, orange, purple, red, or yellow.\n" +
				  "A color may appear more than once in the code.\n" +
				  "You try to guess what colored pegs are in the code and what order they are in.\n" +
				  "After you make a valid guess the result (feedback) will be displayed.\n" +
				  "The result consists of a black peg for each peg you have guessed exactly correct (color and position) in your guess.\n" +
				  "For each peg in the guess that is the correct color, but is out of position, you get a white peg.\n" +
				  " For each peg, which is fully incorrect, you get no feedback.\n" +
				  "Only the first letter of the color is displayed. B for Blue, R for Red, and so forth.\n" +
				  "When entering guesses you only need to enter the first character of each color as a capital letter.\n");
		int size = 0;
		System.out.println("How many pegs would you like in your code?");
		do {
			System.out.println("Please pick a number between 4 and 8.");
			try {
				size = keyboard.nextInt();
			} catch (Exception e) {
				keyboard.next();
				size = 0;
			}
		} while(size < 4 || size > 8);

		String mode = "";
		System.out.println("Which mode would you like to play? Easy (12 tries), Medium (10 tries), or Hard (8 tries)");
		do {
			System.out.println("Please enter either E, M, or H.");
			try {
				mode = keyboard.next();
				mode = mode.toLowerCase();
			} catch (Exception e) {
				mode = "";
			}
		} while (!mode.equals("e") && !mode.equals("m") && !mode.equals("h"));

		int numGuesses = 0;
		if (mode.equals("e")) numGuesses = 12;
		else if (mode.equals("m")) numGuesses = 10;
		else if (mode.equals("h")) numGuesses = 8;

		String start = "";
		do {
			System.out.println("You have " + numGuesses + " guesses to figure out the secret code or you lose the game.  Are you ready to play? (Y/N):");
			try {
				start = keyboard.next();
				start = start.toLowerCase();
			} catch (Exception e) {
				start = "";
			}
		} while (!start.equals("n") && !start.equals("y"));
		if(start.equals("n")) System.exit(0);

		System.out.println("Generating secret code...");
		try {
			key = new Code(size);
		} catch (Exception e) {}
		if(testingMode)
			System.out.println(key.toString());

		boolean winner = false;
		do {
			System.out.println("You have " + numGuesses + " guesses left.");
			while(true) {
				try {
					String guess = "";
					System.out.print("What is your next guess?\n" +
							  "Type in the characters for your guess and press enter.\n" +
							  "Type \"history\" to see your previous guesses.\n" +
							  "Enter guess: ");
					guess = keyboard.next();
					if(guess.equals("history")) {
						displayHistory();
						continue;
					}
					Code code = new Code(size, guess);
					HashMap<String, Integer> result = key.checkGuess(code);
					System.out.println("Result: " + result.get(Code.BLACK_PEG) + " black peg(s)" + " and " + result.get(Code.WHITE_PEG) + " white peg(s)\n");
					if(testingMode)
						System.out.println(key.toString());
					if(result.get(Code.BLACK_PEG) == size)
						winner = true;
					history.addLast(code);
					break;
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
			numGuesses -= 1;
		} while (numGuesses > 0 && !winner);

		if (winner) {
			System.out.println("YOU WIN!!! :D");
		} else {
			System.out.println("You lose... :(");
		}

		System.out.print("Are you ready for another game (Y/N): ");
		if(keyboard.next().toLowerCase().equals("y"))
			start();
	}

	public void displayHistory() {
		int counter = 0;
		for(Code c: history) {
			counter += 1;
			HashMap<String, Integer> result = key.checkGuess(code);
			System.out.println(counter + " " + c.toString() + ": " + result.get(Code.BLACK_PEG) + " black peg(s)" + " and " + result.get(Code.WHITE_PEG) + " white peg(s)");
		}
		if(counter == 0)
			System.out.println("No previous moves.");
		System.out.println();
	}
}
