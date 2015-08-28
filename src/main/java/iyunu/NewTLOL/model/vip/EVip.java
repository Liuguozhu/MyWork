package iyunu.NewTLOL.model.vip;

public enum EVip {

	common(0, 0, 0, 0, 0, 0, 0, 0, 0), // 普通玩家
	gold(6, 0.05, 1, 0, 0, 0.05, 0.1, 0.05, 1), // 黄金VIP
	platinum(12, 0.1, 2, 1, 1, 0.1, 0.15, 0.1, 2), // 白金VIP
	diamond(18, 0.2, 3, 2, 2, 0.2, 0.2, 0.2, 3), // 钻石VIP
	;

	private int energyAdd;
	private double ghostGoldAdd;
	private int shaoxiangAdd;
	private int trialsResetAdd;
	private int yingxiongtieAdd;
	private double partnerExpAdd;
	private double miningGoldAdd;
	private double miningFragmentCost;
	private int qiancengtaAdd;

	EVip(int energyAdd, double ghostGoldAdd, int shaoxiangAdd, int trialsResetAdd, int yingxiongtieAdd, double partnerExpAdd, double miningGoldAdd, double miningFragmentCost, int qiancengtaAdd) {
		this.energyAdd = energyAdd;
		this.ghostGoldAdd = ghostGoldAdd;
		this.shaoxiangAdd = shaoxiangAdd;
		this.trialsResetAdd = trialsResetAdd;
		this.yingxiongtieAdd = yingxiongtieAdd;
		this.partnerExpAdd = partnerExpAdd;
		this.miningGoldAdd = miningGoldAdd;
		this.miningFragmentCost = miningFragmentCost;
		this.qiancengtaAdd = qiancengtaAdd;
	}

	/**
	 * @function 增加活力上限
	 * @author LuoSR
	 * @return 活力
	 * @date 2014年12月9日
	 */
	public int getEnergyAdd() {
		return energyAdd;
	}

	/**
	 * @function 增加追杀令绑银收益
	 * @author LuoSR
	 * @return 收益比率
	 * @date 2014年12月9日
	 */
	public double getGhostGoldAdd() {
		return ghostGoldAdd;
	}

	/**
	 * @function 增加烧香次数
	 * @author LuoSR
	 * @return 次数
	 * @date 2014年12月9日
	 */
	public int getShaoxiangAdd() {
		return shaoxiangAdd;
	}

	/**
	 * @function 增加试练重置次数
	 * @author LuoSR
	 * @return 次数
	 * @date 2014年12月9日
	 */
	public int getTrialsResetAdd() {
		return trialsResetAdd;
	}

	/**
	 * @function 增加英雄帖次数
	 * @author LuoSR
	 * @return 次数
	 * @date 2014年12月9日
	 */
	public int getYingxiongtieAdd() {
		return yingxiongtieAdd;
	}

	/**
	 * @function 增加吞噬伙伴经验
	 * @author LuoSR
	 * @return 增加经验比率
	 * @date 2014年12月9日
	 */
	public double getPartnerExpAdd() {
		return partnerExpAdd;
	}

	/**
	 * @function 收矿/抢矿时，绑银收益增加
	 * @author LuoSR
	 * @return 增加绑银比率
	 * @date 2014年12月9日
	 */
	public double getMiningGoldAdd() {
		return miningGoldAdd;
	}

	/**
	 * @function 抢矿时，有几率不消耗残玉碎片
	 * @author LuoSR
	 * @return 不消耗概率
	 * @date 2014年12月9日
	 */
	public double getMiningFragmentCost() {
		return miningFragmentCost;
	}

	/**
	 * @function 洞天福地重置次数增加
	 * @author LuoSR
	 * @return 不消耗概率
	 * @date 2014年12月9日
	 */
	public int getQiancengtaAdd() {
		return qiancengtaAdd;
	}

}
