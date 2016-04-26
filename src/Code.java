import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ali on 4/26/2016.
 */
public class Code {
	public static final String BLACK_PEG = "black";
	public static final String WHITE_PEG = "white";
	private ArrayList<Peg> key;
	private int size;

	// creates new key randomly
	public Code(int size) throws InvalidGuessException {
		this.size = size;
		key = new ArrayList<>();
		generateRandomKey();
	}

	public Code(int size, String key) throws InvalidGuessException {
		this.size = size;
		this.key = new ArrayList<>();
		for(int i = 0; i < size; i++) {
			this.key.add(new Peg(key.charAt(i)));
		}
	}

	public Code(Code k) {
		size = k.getSize();
		key = k.getKey();
	}

	public int getSize() {
		return size;
	}

	public ArrayList<Peg> getKey() {
		return (ArrayList<Peg>) key.clone();
	}

	public Peg get(int i) {
		return key.get(i);
	}

	public void set(int i, Peg p) {
		key.set(i, p);
	}

	public void generateRandomKey() throws InvalidGuessException {
		for(int i = 0; i < size; i++) {
			int temp = (int) (Math.random() *(Peg.COLORS.length - 1));
			key.add(new Peg(Peg.COLORS[temp]));
		}
	}

	public int contains(Peg temp) {
		for(int i = 0; i < size; i++) {
			if(key.get(i).equals(temp)) return i;
		}
		return -1;
	}

	public boolean compareChar(int i, Code temp) {
		return temp.get(i).equals(key.get(i));
	}

	public HashMap<String, Integer> checkGuess(Code guess) throws InvalidGuessException {
		HashMap<String, Integer> temp = new HashMap<>();
		temp.put(BLACK_PEG, 0);
		temp.put(WHITE_PEG, 0);
		Code tempCode1 = new Code(this);
		Code tempCode2 = new Code(guess);

		for(int i = 0; i < size; i++) {
			if(tempCode1.compareChar(i, tempCode2)) {
				temp.put(BLACK_PEG, temp.get(BLACK_PEG) + 1);
				tempCode1.set(i, new Peg((char) 0));
				tempCode2.set(i, new Peg((char) 0));
			}
		}

		for(int i = 0; i <size; i++) {
			int index = tempCode1.contains(tempCode2.get(i));
			if(index >= 0) {
				temp.put(WHITE_PEG, temp.get(WHITE_PEG) + 1);
				tempCode1.set(index, new Peg((char) 0));
				tempCode2.set(i, new Peg((char) 0));

			}
		}

		return temp;
	}


}
