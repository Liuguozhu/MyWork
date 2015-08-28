package iyunu.NewTLOL.util;

import iyunu.NewTLOL.enumeration.EColor;

public class StringControl {
	/**
	 * 客户端富文本类
	 */

	private StringControl() {

	}

	private static StringControl instance = new StringControl();

	/**
	 * @return StringControl对象
	 */
	public static StringControl instance() {
		return instance;
	}

	/**
	 * 换行
	 * 
	 * @return
	 */
	public static String changeLine() {
		return "$~";
	}

	public static String color(EColor color, String word) {
		switch (color) {
		case green:
			return "<*grn" + word + ">";
		case blue:
			return "<*blu" + word + ">";
		case purple:
			return "<*pur" + word + ">";
		case orange:
			return "<*org" + word + ">";
		default:
			return "<*wht" + word + ">";
		}
	}

	/**
	 * 红
	 * 
	 * @param word
	 * @return
	 */
	public static String red(String word) {
		return "<*red" + word + ">";
	}

	/**
	 * 红
	 * 
	 * @param word
	 * @return
	 */
	public static String red(long num) {
		return "<*red" + num + ">";
	}

	/**
	 * 蓝
	 * 
	 * @param word
	 * @return
	 */
	public static String blu(String word) {
		return "<*blu" + word + ">";
	}

	/**
	 * 白
	 * 
	 * @param word
	 * @return
	 */
	public static String wht(String word) {
		return "<*wht" + word + ">";
	}

	/**
	 * 灰
	 * 
	 * @param word
	 * @return
	 */
	public static String gay(String word) {
		return "<*gay" + word + ">";
	}

	/**
	 * 绿
	 * 
	 * @param word
	 * @return
	 */
	public static String grn(String word) {
		return "<*grn" + word + ">";
	}

	/**
	 * 绿
	 * 
	 * @param word
	 * @return
	 */
	public static String grn(long num) {
		return "<*grn" + num + ">";
	}

	/**
	 * 紫
	 * 
	 * @param word
	 * @return
	 */
	public static String pur(String word) {
		return "<*pur" + word + ">";
	}

	/**
	 * 橙
	 * 
	 * @param word
	 * @return
	 */
	public static String org(String word) {
		return "<*org" + word + ">";
	}

	/**
	 * 黄
	 * 
	 * @param word
	 * @return
	 */
	public static String yel(String word) {
		return "<*yel" + word + ">";
	}

	/**
	 * 浅黄
	 * 
	 * @param word
	 * @return
	 */
	public static String syl(String word) {
		return "<*syl" + word + ">";
	}

}
