/**
 * Ali Tejani amt3639
 * Sonal Jain sj23277
 * Thursday 9:30-11
 * Mastermind
 */

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Game{
	private Code code;
	private int size;
	private LinkedList<Code> history;
	private Scanner keyboard;
	private Code key;
	private boolean testingMode;

	/**
	 * create new game
	 * @param testingModeOn if true, displays key to player
	 */
	public Game(boolean testingModeOn) {
		history = new LinkedList<>();
		keyboard = new Scanner(System.in);
		testingMode = testingModeOn;
	}

	/**
	 * starts new game
	 */
	public void start() {
		JOptionPane.showMessageDialog(null,"\nWelcome to Mastermind. Here are the rules.\n\n" +
				  "This is a text version of the classic board game Mastermind.\n" +
				  "The computer will think of a secret code. The code consists of 4-8 colored pegs.\n" +
				  "The pegs may be one of six colors: blue, green, orange, purple, red, or yellow (or possibly teal or magenta).\n" +
				  "A color may appear more than once in the code.\n" +
				  "You try to guess what colored pegs are in the code and what order they are in.\n" +
				  "After you make a valid guess the result (feedback) will be displayed.\n" +
				  "The result consists of a black peg for each peg you have guessed exactly correct (color and position) in your guess.\n" +
				  "For each peg in the guess that is the correct color, but is out of position, you get a white peg.\n" +
				  " For each peg, which is fully incorrect, you get no feedback.\n" +
				  "Only the first letter of the color is displayed. B for Blue, R for Red, and so forth.\n" +
				  "When entering guesses you only need to enter the first character of each color as a capital letter.\n",
				  "Instructions", JOptionPane.PLAIN_MESSAGE);

		//clear old history
		history.clear();
		
		// gets size of code user wants
		int size;
		do {
			try {
				size = 	Integer.parseInt(JOptionPane.showInputDialog(null,"How many pegs would you like in your code?\nPlease pick a number between 4 and 8." ,JOptionPane.PLAIN_MESSAGE));
			} catch (Exception e) {
				keyboard.next();
				size = 0;
			}
		} while(size < 4 || size > 8);

		// gets number of colors user wants
		int numColors;
		do {
			try {
				numColors = Integer.parseInt(JOptionPane.showInputDialog(null,"How many colors would you like?\nPlease pick a number between 6 and 8.",JOptionPane.PLAIN_MESSAGE));
			} catch (Exception e) {
				keyboard.next();
				numColors = 0;
			}
		} while(numColors < 6 || numColors > 8);

		// gets number of chances user wants
		String mode;
		do {
			try {
				mode = JOptionPane.showInputDialog(null,"Which mode would you like to play? Easy (16 tries), Medium (12 tries), or Hard (8 tries)\nPlease enter either E, M, or H.",JOptionPane.PLAIN_MESSAGE);
				mode = mode.toLowerCase();
			} catch (Exception e) {
				mode = "";
			}
		} while (!mode.equals("e") && !mode.equals("m") && !mode.equals("h"));
		int numGuesses = 0;
		if (mode.equals("e")) numGuesses = 16;
		else if (mode.equals("m")) numGuesses = 12;
		else if (mode.equals("h")) numGuesses = 8;

		// starts game or stops game
		String start;
		do {
			try {
				start = JOptionPane.showInputDialog(null,"You have " + numGuesses + " guesses to figure out the secret code or you lose the game.  Are you ready to play? (Y/N):", JOptionPane.PLAIN_MESSAGE);
				start = start.toLowerCase();
			} catch (Exception e) {
				start = "";
			}
		} while (!start.equals("n") && !start.equals("y"));
		if(start.equals("n")) System.exit(0);

		// generates password
		JOptionPane.showMessageDialog(null,"Generating secret code...", null, JOptionPane.PLAIN_MESSAGE);
		try {
			key = new Code(size, numColors);
		} catch (Exception e) {}
		if(testingMode)
		{
			JOptionPane.showMessageDialog(null, key.toString(), "Secret Code", JOptionPane.PLAIN_MESSAGE);
		}


		// asks user for guess
		boolean winner = false;
		do {
			JOptionPane.showMessageDialog(null,"You have " + numGuesses + " guesses left.");
			while(true) {
				try {
					String guess = "";
					guess = JOptionPane.showInputDialog(null,"What is your next guess?\n" +
							  "Type in the characters for your guess and press enter.\n" +
							  "Type \"history\" to see your previous guesses.\n" +
							  "Enter guess: ", JOptionPane.PLAIN_MESSAGE);
					if(guess.equals("history")) {
						displayHistory();
						continue;
					}
					Code code = new Code(size, numColors, guess);
					HashMap<String, Integer> result = key.checkGuess(code);
					JOptionPane.showMessageDialog(null,"Result: " + result.get(Code.BLACK_PEG) + " black peg(s)" + " and " + result.get(Code.WHITE_PEG) + " white peg(s)\n");
					if(testingMode)
					{
						JOptionPane.showMessageDialog(null, key.toString(), "Secret Code", JOptionPane.PLAIN_MESSAGE);
					}
					if(result.get(Code.BLACK_PEG) == size)
						winner = true;
					history.addLast(code);
					break;
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
			numGuesses -= 1;
		} while (numGuesses > 0 && !winner);

		if (winner) {
			JOptionPane.showMessageDialog(null, "YOU WIN!!! :D");

		} else {
			JOptionPane.showMessageDialog(null, "You lose... :(");
		}

		String guess = JOptionPane.showInputDialog(null,"Are you ready for another game (Y/N): ", JOptionPane.PLAIN_MESSAGE);
		if(guess.toLowerCase().equals("y"))
			start();
	}

	/**
	 * displays users guess history
	 */
	public void displayHistory() {
		int counter = 0;
		String temp = "";
		for(Code c: history) {
			counter += 1;
			HashMap<String, Integer> result = key.checkGuess(c);
			temp +=  counter + ". " + c.toString() + ": " + result.get(Code.BLACK_PEG) + " black peg(s)" + " and " + result.get(Code.WHITE_PEG) + " white peg(s)\n";
		}
		if(counter == 0)
		{
			JOptionPane.showMessageDialog(null, "No previous moves.");
		} else {
			JOptionPane.showMessageDialog(null, temp);
		}

		
	}
}
