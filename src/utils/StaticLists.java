package utils;

public class StaticLists {
	public static Lists STATICK_LISTS;
	
	public static void initStaticLists(String path) {
		Parser myParser = new Parser();
		STATICK_LISTS = new Lists();

		myParser.setPath(path);
		myParser.parse(STATICK_LISTS);
		
		System.out.println("[INFO] > Static lists init...");
	}
}
