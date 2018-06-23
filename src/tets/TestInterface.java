package tets;
import java.io.IOException;

import utils.InterfaceConsole;

public class TestInterface {

	public static void main(String[] args) {
		
		try {
			InterfaceConsole.initConsole();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
