/**
 * Ali Tejani amt3639
 * Sonal Jain sj23277
 * Thursday 9:30-11
 * Mastermind
 */

import java.util.ArrayList;
import java.util.HashMap;

public class Code {
	public static final String BLACK_PEG = "black";
	public static final String WHITE_PEG = "white";
	private ArrayList<Peg> key;
	private int size;
	private int numColors;

	/**
	 * creates new code randomly
	 * @param size size of code
	 * @param numColors number of colors in code
	 * @throws InvalidGuessException thrown if code is invalid
	 */
	public Code(int size, int numColors) throws InvalidGuessException {
		this.size = size;
		key = new ArrayList<>();
		generateRandomKey(numColors);
		this.numColors = numColors;
	}

	/**
	 * creates new code from given input
	 * @param size size of code
	 * @param numColors number of colors in code
	 * @param key code is created from string
	 * @throws InvalidGuessException thrown if code is invalid
	 */
	public Code(int size, int numColors, String key) throws InvalidGuessException {
		if(size != key.length())
			throw new InvalidGuessException("Invalid Guess");
		this.size = size;
		this.key = new ArrayList<>();
		this.numColors = numColors;
		for(int i = 0; i < size; i++) {
			this.key.add(new Peg(key.charAt(i), numColors));
		}
	}

	/**
	 * copy constructor
	 * @param k copies over data from k to this
	 */
	public Code(Code k) {
		size = k.getSize();
		key = k.getKey();
	}

	/**
	 * returns Code as a string
	 * @return all the colors of pegs in code
	 */
	public String toString() {
		String temp = "";
		for(Peg p: key) {
			temp = temp + p.getColor();
		}
		return temp;
	}

	/**
	 * gets size of code
	 * @return size of code
	 */
	public int getSize() {
		return size;
	}

	/**
	 * gets code
	 * @return key
	 */
	public ArrayList<Peg> getKey() {
		return (ArrayList<Peg>) key.clone();
	}

	/**
	 * gets peg at index i
	 * @param i index of peg to get
	 * @return returns peg
	 */
	public Peg get(int i) {
		return key.get(i);
	}

	/**
	 * sets peg at index i
	 * @param i index of peg to set
	 * @param p new value of peg
	 */
	public void set(int i, Peg p) {
		key.set(i, p);
	}

	/**
	 * creates random key
	 */
	public void generateRandomKey(int numColors) throws InvalidGuessException {
		for(int i = 0; i < size; i++) {
//			int temp = (int) (Math.random() *(numColors));
//			key.add(new Peg(Peg.COLORS[temp], numColors));
			key.add(new Peg(numColors));
		}
	}

	/**
	 * returns index of peg given
	 */
	public int contains(Peg temp) {
		for(int i = 0; i < size; i++) {
			if(key.get(i).equals(temp)) return i;
		}
		return -1;
	}

	/**
	 * returns true if pegs are the same at the same index
	 */
	public boolean compareChar(int i, Code temp) {
		return temp.get(i).equals(key.get(i));
	}

	/**
	 * returns hashmap of how many black pegs and how many white pegs there are
	 */
	public HashMap<String, Integer> checkGuess(Code guess) {
		HashMap<String, Integer> temp = new HashMap<>();
		temp.put(BLACK_PEG, 0);
		temp.put(WHITE_PEG, 0);
		try {
			Code tempCode1 = new Code(this);
			Code tempCode2 = new Code(guess);

			for (int i = 0; i < size; i++) {
				if (tempCode1.compareChar(i, tempCode2)) {
					temp.put(BLACK_PEG, temp.get(BLACK_PEG) + 1);
					tempCode1.set(i, new Peg((char) 0, numColors));
					tempCode2.set(i, new Peg((char) 0, numColors));
				}
			}

			for (int i = 0; i < size; i++) {
				int index = tempCode1.contains(tempCode2.get(i));
				if (index >= 0) {
					temp.put(WHITE_PEG, temp.get(WHITE_PEG) + 1);
					tempCode1.set(index, new Peg((char) 0, numColors));
					tempCode2.set(i, new Peg((char) 0, numColors));

				}
			}
	} catch (Exception e) {}

		return temp;
	}


}
