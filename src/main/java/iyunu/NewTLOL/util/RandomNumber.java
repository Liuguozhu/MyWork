package iyunu.NewTLOL.util;

import java.util.Random;

public final class RandomNumber {

	/**
	 * 私有构造方法
	 */
	private RandomNumber() {

	}

	private static Random RANDOM = new Random();

	private static final int TOTAL = 45; // 总和值
	private static final int COUNT = 4; // 单值的个数
	private static final int MAX = 15; // 单值的上限
	private static final int MIN = 5; // 单值的下限

	/**
	 * 随机数值
	 * 
	 * @return 数组
	 */
	public static int[] random() {

		int[] number = new int[COUNT];
		count(TOTAL, COUNT, number);

		// ======日志======
		System.out.print("伙伴资质参数A  B  C  D：");
		for (int i = 0; i < COUNT; i++) {
			System.out.print(number[i] + "  ");
		}
		// ======日志======

		return number;
	}

	/**
	 * 计算
	 * 
	 * @param total
	 *            当前的总和值
	 * @param count
	 *            当前的单值个数
	 * @param number
	 *            存储容器
	 */
	private static void count(int total, int count, int[] number) {
		int index = count - 1; // 获取剩下的单值个数
		int rand = getRandom(); // 随即获取一个单值
		int temp = total - rand; // 获取剩下的总和值
		if (temp >= MIN * index && temp <= MAX * index) { // 如果剩下的总和值在合理范围区间，则刚才取的单值符合条件
			number[index] = rand; // 记录单值
			if (index == 1) { // 如果剩下的单值个数为1，则剩下的总和值即为最后一个单值
				number[index - 1] = temp; // 记录最后一个单值
				return; // 退出计算
			}
			count(temp, index, number); // 获取下一个单值
		} else { // 如果剩下的总和值不在合理范围区间，则刚才取的单值不符合条件，重新获取此次单值
			count(total, count, number); // 重新获取此次单值
		}
	}

	/**
	 * 获取随机单值
	 * 
	 * @return 随机单值
	 */
	private static int getRandom() {
		return RANDOM.nextInt(MAX - MIN) + MIN;
	}

}