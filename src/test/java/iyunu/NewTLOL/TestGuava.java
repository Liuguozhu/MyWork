package iyunu.NewTLOL;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Table;

public class TestGuava {

	@Test
	public void test() {
		BiMap<Integer, Integer> map = HashBiMap.create();

		map.put(1, 100);
		map.put(2, 200);
		map.put(3, 300);
		map.put(4, 400);

		System.out.println(map.get(1));
		System.out.println(map.inverse().get(100));

		Table<Integer, Integer, String> weightedGraph = HashBasedTable.create();

		weightedGraph.put(1, 1, "a");

		weightedGraph.put(1, 2, "b");

		weightedGraph.put(2, 1, "c");
		weightedGraph.put(2, 2, "d");

		System.out.println(weightedGraph);
		System.out.println(weightedGraph.row(1));
		System.out.println(weightedGraph.column(1));
		; // returns a Map mapping v2 to 4, v3 to 20

		weightedGraph.column(3); // returns a Map mapping v1 to 20, v2 to 5

		ArrayListMultimap<String, Integer> hashMultimap = ArrayListMultimap.create();
		hashMultimap.put("a", 1);
		hashMultimap.put("a", 2);
		List<Integer> list = hashMultimap.get("b");
		System.out.println(list);
		// hashMultimap.ke
		// Multimaps.asMap(hashMultimap).get(key)
		// hashMultimap.asMap().get(key);
	}

}
