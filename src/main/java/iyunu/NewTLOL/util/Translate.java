package iyunu.NewTLOL.util;

/**
 * 转换类
 * 
 * @author SunHonglei
 * 
 */
public class Translate {

	/**
	 * 字符串转整型
	 * 
	 * @param str
	 *            字符串
	 * @return 整型数字
	 */
	public static int stringToInt(String str) {
		if (str == null || "".equals(str)) {
			return 0;
		}
		return Integer.valueOf(str);
	}

	/**
	 * 字符串转整型
	 * 
	 * @param str
	 *            字符串
	 * @return 整型数字
	 */
	public static int bagStringToInt(String str) {
		if (str == null || "".equals(str)) {
			return -1;
		}
		return Integer.valueOf(str);
	}

	/**
	 * 字符串转长整型
	 * 
	 * @param str
	 *            字符串
	 * @return 长整型数字
	 */
	public static long stringToLong(String str) {
		if (str == null) {
			return 0;
		}
		return Long.valueOf(str);
	}

	/**
	 * Long转成long
	 * 
	 * @param obl
	 *            Long对象
	 * @return long数值
	 */
	public static long longToLong(Long obl) {
		if (obl == null) {
			return 0;
		}
		return obl;
	}

	/**
	 * Integer转int
	 * 
	 * @param value
	 * @return
	 */
	public static int integerToInt(Integer value) {
		if (value == null) {
			return 0;
		}
		return value;
	}

	/**
	 * string转boolean
	 * 
	 * @param value
	 * @return
	 */
	public static boolean stringToBoolean(String value) {

		return Boolean.valueOf(value);
	}

	/**
	 * long 转 int
	 * 
	 * @param value
	 * @return
	 */
	public static int longToInt(long value) {
		return (int) value;
	}

	public static int doubeToInt(Double value) {
		if (value != null) {
			return value.intValue();
		}
		return 0;
	}
}
