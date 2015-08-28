package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.util.Util;

import java.util.List;

public class StoneManager {
	private static StoneManager instance = new StoneManager();
	/** 宝石合成系数 */
	public final double coefficient = 1.2;

	public static StoneManager instance() {
		return instance;
	}

	private StoneManager() {

	}

	/**
	 * * 同级宝石 每1颗提供25%的成功率 高1级宝石 高1级基础成功率75%，每高1级基础成功率增加25% 低1级宝石
	 * 每1颗基础成功率12.5%，每低1级降低5%成功率，最低成功率0.25% 宝石合成执行
	 * 
	 * @param mainLevel
	 *            主宝石等级
	 * @param auxiliaryNum
	 *            副宝石等级和
	 * @return 合成成功还是失败
	 */

	public boolean up(int mainLevel, List<Integer> list) {
		float chance = 0.0f;
		for (Integer fuLevel : list) {
			if (mainLevel == fuLevel) {
				chance = chance + 0.25f;
			} else if (mainLevel < fuLevel) {
				if (fuLevel - mainLevel == 1) {
					chance = chance + 0.75f;
				} else {

					chance = chance + 0.75f + ((fuLevel - mainLevel - 1) * 0.25f);
				}
			} else {
				if (mainLevel - fuLevel == 1) {
					chance = chance + 0.125f;
				} else {
					chance = chance + 0.125f - ((mainLevel - fuLevel - 1) * 0.05f);
				}
			}
		}
		if (chance < 0.0025f * list.size()) {
			chance = 0.0025f * list.size();
		}

//		System.out.println("总成功率:" + chance);
		if (Math.random() <= chance) {
			return true;
		}
		return false;
	}

	/**
	 * 是否降级 ，50%机率降级
	 * 
	 * @param mainLevel
	 *            主宝石等级
	 * @return false是不降级，true是降级 1级没法降所以不降级
	 */
	public boolean reduceLevel(int mainLevel) {
		if (mainLevel == 1) {
			return false;
		}
		if (Util.RANDOM.nextInt(2) == 1) {
			return false;
		}
		return true;
	}

}