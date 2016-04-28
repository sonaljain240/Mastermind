/**
 * Created by Ali on 4/26/2016.
 */
public class Peg {
	private char color;
	private int numColors;
	public final static char[] COLORS = {'B', 'G', 'O', 'P', 'R', 'Y', 'T', 'M'};


	/**
	 * creates new peg random peg
	 */
	public Peg(int numColors) {
		color = COLORS[(int) (Math.random() * numColors)];
		this.numColors = numColors;
	}

	/**
	 * creates new peg with given color
	 */
	public Peg(char c, int numColors) throws InvalidGuessException{
		this.numColors = numColors;
		if(!isColor(c))
			throw new InvalidGuessException("Invalid Guess");
		color = c;
	}

	/**
	 * returns color
	 */
	public char getColor() {
		return color;
	}

	/**
	 * sets color to c
	 */
	public void setColor(char c) {
		color = c;
	}

	/**
	 * returns true if colors are equal
	 */
	public boolean equals(Peg p) {
		return color != 0 && color == p.getColor();
	}

	/**
	 * returns true if c is a color
	 */
	public boolean isColor(char c) {
		for(int i = 0; i < numColors; i++)
			if(COLORS[i] == c || c == 0)
				return true;
		return false;
	}
}
