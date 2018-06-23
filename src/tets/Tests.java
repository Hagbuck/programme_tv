package tets;

import prog.*;
import utils.*;

public class Tests {

	public static void main(String[] args) {
		
		Parser myParser = new Parser();
		myParser.setPath("ptv.xml");
		myParser.parse();

		System.out.println(myParser.nbOfChannels);

		System.out.println(myParser.EmissionList);
		for(int i=1;i<myParser.EmissionList.size();i++)
		{
			System.out.println(myParser.EmissionList.get(i));
		}
		
	}

}
