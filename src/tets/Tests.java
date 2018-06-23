package tets;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import prog.*;
import utils.*;

public class Tests {

	public static void main(String[] args) {
		
		Parser myParser = new Parser();
		myParser.setPath("ptv.xml");
		Lists myLists = new Lists();
		myParser.parse(myLists);
	
		System.out.println();
		System.out.println("---- Affichage du nombre de Channels ----");
		System.out.println(myLists.nbOfChannels);
		
		System.out.println();
		System.out.println("---- Affichage des Channels ----");
		System.out.println(myLists.channelsList);
		
		System.out.println();
		System.out.println("---- Affichage des Emissions ----");
		System.out.println(myLists.EmissionList);
		
		System.out.println();
		System.out.println("---- Affichage des emissions par date ----");
		System.out.println(myLists.programOfADay);
		

	}

}
