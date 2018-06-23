package tets;
import java.io.IOException;

import utils.InterfaceConsole;
import utils.Lists;

public class TestInterface {

	public static void main(String[] args) {
		
		
		try {
			InterfaceConsole.initConsole(new Lists());
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
