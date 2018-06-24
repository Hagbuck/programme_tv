package tets;
import java.io.IOException;

import utils.InterfaceConsole;
import utils.Lists;
import utils.Parser;

public class TestInterface {

	public static void main(String[] args) {
		
		
		try {
			Parser myParser = new Parser();
			myParser.setPath("ptv.xml");
			Lists myLists = new Lists();
			myParser.parse(myLists);
			
			InterfaceConsole.initConsole(myLists);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
