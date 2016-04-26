/**
 * Created by Ali on 4/26/2016.
 */
public class Driver {
	public static void main(String[] args) {
		Code a = new Code(4, "ROBO");
		Code b = new Code(4, "OGOO");
		System.out.println(a.checkGuess(b).toString());

	}
}
