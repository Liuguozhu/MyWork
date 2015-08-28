package iyunu.NewTLOL.util;

public class Convert {

	public static int parseInt(String intStr) {
		return parseInt(intStr, 0);
	}

	public static int parseInt(String intStr, int defaultInt) {
		try {
			return Integer.parseInt(intStr);
		} catch (Exception e) {
			return defaultInt;
		}
	}
}
