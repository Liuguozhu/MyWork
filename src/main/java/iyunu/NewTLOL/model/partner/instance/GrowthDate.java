package iyunu.NewTLOL.model.partner.instance;


/**
 * @function 伙伴进阶数据对象类
 * @author LuoSR
 * @date 2014年3月3日
 */
public class GrowthDate {
	/** 成长值 **/
	private int growth;
	/** 成长概率 **/
	private int growthProbability;

	public int getGrowth() {
		return growth;
	}

	public void setGrowth(int growth) {
		this.growth = growth;
	}

	public int getGrowthProbability() {
		return growthProbability;
	}

	public void setGrowthProbability(int growthProbability) {
		this.growthProbability = growthProbability;
	}

}
