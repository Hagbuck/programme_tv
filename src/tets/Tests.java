package tets;

import prog.*;
import utils.*;

public class Tests {

	public static void main(String[] args) {
		
		Parser myParser = new Parser();
		myParser.setPath("ptv.xml");
		myParser.parse();

		System.out.println(myParser.nbOfChannels);

		for(int i=0;i<myParser.EmissionList.size();i++)
		{
			System.out.println(myParser.EmissionList.get(i).toString());
		}
		
	}

}
