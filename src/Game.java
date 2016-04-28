/**
 * Ali Tejani amt3639
 * Sonal Jain sj23277
 * Thursday 9:30-11
 * Mastermind
 */

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
		int size = 0;
		try{
			String[] choices = { "4", "5", "6", "7", "8"};
			size = Integer.parseInt((String) JOptionPane.showInputDialog(null, "How many pegs would you like in your code?",
				        null, JOptionPane.QUESTION_MESSAGE, null, choices, // Array of choices
				        choices[0])); // Initial choice
			
		}
		catch(Exception e){
			System.exit(0);
		}
		// gets number of colors user wants
		int numColors = 0;
		try{
			String[] choices = {"6", "7", "8"};
			numColors = Integer.parseInt((String) JOptionPane.showInputDialog(null, "How many colors would you like?",
				        null, JOptionPane.QUESTION_MESSAGE, null, choices, // Array of choices
				        choices[0])); // Initial choice
		}
		catch(Exception e){
			System.exit(0);
		}

		// gets number of chances user wants
		String mode = "";
		try{
			String[] choices = { "Easy (16 tries)", "Medium (12 tries)", "Hard (8 tries)"};
			mode = (String) JOptionPane.showInputDialog(null, "What difficulty would you like to play?",
				        null, JOptionPane.QUESTION_MESSAGE, null, choices, // Array of choices
				        choices[1]); // Initial choice
		}
		catch(Exception e){
			System.exit(0);
		}
		int numGuesses = 0;
		if (mode.equals("Easy (16 tries)")) numGuesses = 16;
		else if (mode.equals("Medium (12 tries)")) numGuesses = 12;
		else if (mode.equals("Hard (8 tries)")) numGuesses = 8;

		// starts game or stops game
		int start = 0;
		try{
			start =  JOptionPane.showOptionDialog(null, "You have " + numGuesses + " guesses to figure out the secret code or you lose the game.  Are you ready to play?",
				        null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null); // Initial choice
		}
		catch(Exception e){
			System.exit(0);
		}
	
		if(start == JOptionPane.NO_OPTION) System.exit(0);

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
					Object[] options1 = { "OK", "History", "Cancel" };

					JPanel panel = new JPanel();
					panel.add(new JLabel("Enter guess: "));
					JTextField textField = new JTextField(10);
					panel.add(textField);

					int res = JOptionPane.showOptionDialog(null, panel, "What is your next guess?",
	                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
	                	null, options1, JOptionPane.YES_OPTION);
					if (res == JOptionPane.YES_OPTION){
						guess = textField.getText();
					} 
					else if(res == JOptionPane.NO_OPTION){
						displayHistory();
						continue;
					}
					else{
						System.exit(0);
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
		int s = 0;
		try{
			s =  JOptionPane.showOptionDialog(null, "You have " + numGuesses + " guesses to figure out the secret code or you lose the game.  Are you ready to play?",
				        null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null); // Initial choice
		}
		catch(Exception e){
			System.exit(0);
		}
			if(s == JOptionPane.YES_OPTION){
				start();
			}
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
