package iyunu.NewTLOL.model.intensify.res;

import iyunu.NewTLOL.model.intensify.instance.IntensifyResolve;
import iyunu.NewTLOL.util.Translate;

/**
 * 装备分解
 * 
 * @author SunHonglei
 * 
 */
public class IntensifyResolveRes {

	private int start;
	private String item;

	public IntensifyResolve toIntensifyResolve() {
		IntensifyResolve intensifyResolve = new IntensifyResolve();
		intensifyResolve.setStart(start);
		if (item != null && !"".equals(item)) {
			String[] strings = item.split(";");
			for (String string : strings) {
				String[] strs = string.split(":");
				intensifyResolve.getItem().put(Translate.stringToInt(strs[0]), Translate.stringToInt(strs[1]));
			}
		}
		return intensifyResolve;
	}

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
	public String getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(String item) {
		this.item = item;
	}

}
