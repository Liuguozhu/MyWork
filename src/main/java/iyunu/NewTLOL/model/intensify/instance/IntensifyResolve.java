package iyunu.NewTLOL.model.intensify.instance;

import java.util.HashMap;

/**
 * 装备分解
 * 
 * @author SunHonglei
 * 
 */
public class IntensifyResolve {

	private int start;
	private HashMap<Integer, Integer> item = new HashMap<Integer, Integer>();

	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @return the item
	 */
	public HashMap<Integer, Integer> getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(HashMap<Integer, Integer> item) {
		this.item = item;
	}

}
