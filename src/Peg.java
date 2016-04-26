/**
 * Created by Ali on 4/26/2016.
 */
public class Peg {
	private char color;
	public final static char[] COLORS = {'B', 'G', 'O', 'P', 'R', 'Y'};


	// if not valid color, throw exception
	public Peg(char c) throws InvalidGuessException{
		if(!isColor(c))
			throw new InvalidGuessException("Invalid Guess");
		color = c;
	}

	public char getColor() {
		return color;
	}

	public void setColor(char c) {
		color = c;
	}

	public boolean equals(Peg p) {
		return color != 0 && color == p.getColor();
	}

	public static boolean isColor(char c) {
		for(char temp: COLORS)
			if(temp == 0 || temp == c)
				return true;
		return false;
	}
}
