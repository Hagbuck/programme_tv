package utils;

import java.util.Comparator;
import java.util.TreeMap;

public class TreeValuesComparator implements Comparator<String> {

	TreeMap<String,Integer> base;
	
	public TreeValuesComparator(TreeMap<String, Integer> base) {
		this.base = base;
	}

	public int compare(String a, String b) {
		if (base.get(a) >= base.get(b)) {
			return -1;
		} else {
			return 1;
		}
	}
}
