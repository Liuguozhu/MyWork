package iyunu.NewTLOL.util;

import java.util.Random;
import java.util.regex.Pattern;

public class Util {

	/** 随机 **/
	public static final Random RANDOM = new Random();
	/** 概率分母 **/
	private static final int DENOMINATOR = 10000;

	/**
	 * 获取95%至105%的概率
	 * 
	 * @return 0.95至1.05的小数
	 */
	public static float getRandomHarm() {
		return (RANDOM.nextInt(1000) + 9500) / 10000f;
	}

	/**
	 * 根据给定范围取随机一个整数
	 * 
	 * @param min
	 *            随机下限（包含）
	 * @param max
	 *            随机上限（包含）
	 * @return int型随机数
	 */
	public static int getRandom(final int min, final int max) {
		if (min > max) {
			return min > 0 ? min : 1;
		}
		return RANDOM.nextInt(max - min + 1) + min;
	}

	/**
	 * 根据给定范围取随机一个整数
	 * 
	 * @param max
	 *            随机上限（不包含）
	 * @return int型随机数
	 */
	public static int getRandom(final int max) {
		if (max <= 0) {
			return 0;
		}
		return RANDOM.nextInt(max);
	}

	/**
	 * 可能性
	 * 
	 * @return 是、否
	 */
	public static boolean probable() {
		return RANDOM.nextBoolean();
	}

	/**
	 * 可能性
	 * 
	 * @param num
	 *            概率
	 * @return 是、否
	 */
	public static boolean probable(int num) {
		if (RANDOM.nextInt(DENOMINATOR) > num) {
			return false;
		}
		return true;
	}

	/**
	 * 可能性
	 * 
	 * @param num
	 *            概率
	 * @return 是、否
	 */
	public static boolean probable(int num, int sum) {
		if (sum == 0 || RANDOM.nextInt(sum) > num) {
			return false;
		}
		return true;
	}

	/**
	 * 可能性
	 * 
	 * @param num
	 *            float数值 概率
	 * @return 是、否
	 */
	public static boolean probable(float num) {
		if (RANDOM.nextFloat() > num) {
			return false;
		}
		return true;
	}

	/**
	 * 字符串匹配（数字、字母）
	 * 
	 * @param str
	 *            字符串
	 * @return 匹配结果
	 */
	public static boolean matchWithoutZn(String str) {
		return Pattern.matches("[a-zA-Z0-9]*", str);
	}

	/**
	 * 比较最小的数字
	 * 
	 * @param num
	 *            数字集合
	 * @return 最小的数字
	 */
	public static int matchMin(int... num) {
		int min = num[0];
		for (int i = 1; i < num.length; i++) {
			min = min > num[i] ? num[i] : min;
		}
		return min;
	}

	/**
	 * 比较最大的数字
	 * 
	 * @param num
	 *            数字集合
	 * @return 最大的数字
	 */
	public static int matchMax(int... num) {
		int max = num[0];
		for (int i = 1; i < num.length; i++) {
			max = max < num[i] ? num[i] : max;
		}
		return max;
	}

	/**
	 * 判断指定数字是否大于0，若小于0则返回0
	 * 
	 * @param num
	 *            指定数字
	 * @return 结果
	 */
	public static int matchZero(int num) {
		return num > 0 ? num : 0;
	}

	/**
	 * 判断指定数字是否大于0，若小于0则返回0
	 * 
	 * @param num
	 *            指定数字
	 * @return 结果
	 */
	public static long matchZero(long num) {
		return num > 0 ? num : 0;
	}

	/**
	 * 获取比较大的数字
	 * 
	 * @param number1
	 *            指定数字1
	 * @param number2
	 *            指定数字2
	 * @return 比较大的数字
	 */
	public static int matchBigger(int number1, int number2) {
		return number1 > number2 ? number1 : number2;
	}

	/**
	 * 获取比较小的数字
	 * 
	 * @param number1
	 *            指定数字1
	 * @param number2
	 *            指定数字2
	 * @return 比较小的数字
	 */
	public static int matchSmaller(int number1, int number2) {
		return number1 < number2 ? number1 : number2;
	}

	/**
	 * 获取比较小的数字
	 * 
	 * @param number1
	 *            指定数字1
	 * @param number2
	 *            指定数字2
	 * @return 比较小的数字
	 */
	public static float matchSmaller(float number1, float number2) {
		return number1 < number2 ? number1 : number2;
	}

	/**
	 * true/false转为0/1
	 * 
	 * @param result
	 *            true/false
	 * @return 0/1
	 */
	public static int trueOrFalse(final boolean result) {
		if (result) {
			return 0;
		}
		return 1;
	}

	/**
	 * true/false转为1/0
	 * 
	 * @param result
	 *            true/false
	 * @return 1/0
	 */
	public static int falseOrTrue(final boolean result) {
		if (result) {
			return 1;
		}
		return 0;
	}

	/**
	 * 0/1转为 true/false
	 * 
	 * @param result
	 *            0/1
	 * @return true/false
	 */
	public static boolean trueOrFalse(final int result) {
		if (result == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 1/0转为 true/false
	 * 
	 * @param result
	 *            1/0
	 * @return true/false
	 */
	public static boolean falseOrTrue(final int result) {
		if (result == 1) {
			return true;
		}
		return false;
	}
}
