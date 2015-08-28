package iyunu.NewTLOL.model.activity;

/**
 * 寻宝
 * 
 * @author SunHonglei
 * 
 */
public class HuntTreasureInfo {

	private int index; // 索引
	private int item; // 藏宝图编号
	private int probability1; // 概率
	private int probability2; // 概率
	private int isBind; // 是否绑定

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the item
	 */
	public int getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(int item) {
		this.item = item;
	}

	/**
	 * @return the probability1
	 */
	public int getProbability1() {
		return probability1;
	}

	/**
	 * @param probability1
	 *            the probability1 to set
	 */
	public void setProbability1(int probability1) {
		this.probability1 = probability1;
	}

	/**
	 * @return the probability2
	 */
	public int getProbability2() {
		return probability2;
	}

	/**
	 * @param probability2
	 *            the probability2 to set
	 */
	public void setProbability2(int probability2) {
		this.probability2 = probability2;
	}

	/**
	 * @return the isBind
	 */
	public int getIsBind() {
		return isBind;
	}

	/**
	 * @param isBind
	 *            the isBind to set
	 */
	public void setIsBind(int isBind) {
		this.isBind = isBind;
	}

}
