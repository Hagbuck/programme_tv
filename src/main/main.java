package main;

import java.io.IOException;
import utils.InterfaceConsole;
import utils.Lists;
import utils.Parser;

public class main {

	public static void main(String[] args) {
		
		try {
			//REPLACE PATH IF YOU NEED
			String pathXML = "ptvCP.xml";
			
			//INIT PARSER
			Parser myParser = new Parser();
			myParser.setPath(pathXML);
			//INIT OBJECT LIST & PARSE
			Lists myLists = new Lists();
			myParser.parse(myLists);
			//INIT CONSOLE
			InterfaceConsole.initConsole(myLists);
			
		} 
		
		catch (IOException e) 
		{
			System.out.println("[ERROR] - Impossible de charger l'application.");
		}

	}

}
