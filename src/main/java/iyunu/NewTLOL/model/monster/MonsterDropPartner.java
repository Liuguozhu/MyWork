package iyunu.NewTLOL.model.monster;

/**
 * 怪物掉落伙伴类
 * 
 * @author SunHonglei
 * 
 */
public class MonsterDropPartner {

	private int partnerIndex; // 伙伴编号
	private int num; // 数量
	private int probability; // 概率
	private int isBind; // 绑定（0.不绑定，1.绑定）

	/**
	 * 构造方法 怪物掉率物品类
	 * 
	 * @param partnerIndex
	 *            伙伴编号
	 * @param num
	 *            数量
	 * @param probability
	 *            掉落概率
	 * @param isBind
	 *            绑定
	 */
	public MonsterDropPartner(int partnerIndex, int num, int probability, int isBind) {
		this.partnerIndex = partnerIndex;
		this.num = num;
		this.probability = probability;
		this.isBind = isBind;
	}

	/**
	 * @return the partnerIndex
	 */
	public int getPartnerIndex() {
		return partnerIndex;
	}

	/**
	 * @param partnerIndex
	 *            the partnerIndex to set
	 */
	public void setPartnerIndex(int partnerIndex) {
		this.partnerIndex = partnerIndex;
	}

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num
	 *            the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * @return the probability
	 */
	public int getProbability() {
		return probability;
	}

	/**
	 * @param probability
	 *            the probability to set
	 */
	public void setProbability(int probability) {
		this.probability = probability;
	}

	public int getIsBind() {
		return isBind;
	}

	public void setIsBind(int isBind) {
		this.isBind = isBind;
	}

}
