package iyunu.NewTLOL.manager;

public class BuyGoldManager {

	public static int USE_BUY_GOLD_NUM = 0;
	public static int FREE_BUY_GOLD_NUM = 5;
	public static int MONEY_BUY_GOLD_NUM = 100;

	private static BuyGoldManager instance = new BuyGoldManager();

	public static BuyGoldManager instance() {
		return instance;
	}

	private BuyGoldManager() {
	}

	/**
	 * @function 根据招财次数计算花费元宝
	 * @author LuoSR
	 * @param num
	 *            元宝招财次数
	 * @return 要消耗的元宝数
	 * @date 2014年9月9日
	 */
	public int getMoneyByNum(int num) {
		int money = 0;
		if (num <= 1) {
			money = 1;
		} else if (num <= 2) {
			money = 2;
		} else if (num <= 3) {
			money = 3;
		} else if (num <= 4) {
			money = 4;
		} else if (num <= 5) {
			money = 5;
		} else if (num <= 6) {
			money = 6;
		} else if (num <= 7) {
			money = 7;
		} else if (num <= 8) {
			money = 8;
		} else if (num <= 9) {
			money = 9;
		} else {
			money = 10;
		}

		return money;
	}
}
