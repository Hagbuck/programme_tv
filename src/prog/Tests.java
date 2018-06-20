package prog;

public class Tests {

	public static void main(String[] args) {
		
		Parser myParser = new Parser();
		myParser.setPath("ptv.xml");
		myParser.parse();

		System.out.println(myParser.nbOfChannels);
		
	}

}
