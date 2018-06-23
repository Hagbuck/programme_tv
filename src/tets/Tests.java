package tets;

import prog.*;
import utils.*;

public class Tests {

	public static void main(String[] args) {
		
		Parser myParser = new Parser();
		myParser.setPath("ptv.xml");
		Lists myLists = new Lists();
		myParser.parse(myLists);
	

		System.out.println("\n\n---- Affichage du nombre de Channels ----");
		System.out.println(myLists.nbOfChannels);
		
		System.out.println("\n\n---- Affichage des Channels ----");
		System.out.println(myLists.channelsList);
		
		System.out.println("\n\n---- Affichage des Emissions ----");
		System.out.println(myLists.EmissionList);
		
		System.out.println("\n\n---- Affichage des emissions par date ----");
		System.out.println(myLists.programOfADay);
		
		System.out.println("\n\n---- Affichage treemap date debut ----");
		System.out.println(myLists.emissionBegin);
		
		System.out.println("\n\n---- Affichage treemap date fin ----");
		System.out.println(myLists.emissionEnd);
		
		

	}

}
