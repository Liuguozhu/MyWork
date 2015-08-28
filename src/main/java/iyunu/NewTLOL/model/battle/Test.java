package iyunu.NewTLOL.model.battle;

import java.util.HashMap;
import java.util.Map;

public class Test {

	public static <T1, T2> void test(Map<T1, T2> map, Class<T1> clazz, Class<T2> clazzb) {
		map.entrySet().iterator();
	}

	public static void aa() {

		Map<Integer, String> map = new HashMap<Integer, String>();
		test(map, Integer.class, String.class);

	}
}
