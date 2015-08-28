package iyunu.NewTLOL.model.activity;

/**
 * 转盘
 * 
 * @author SunHonglei
 * 
 */
public class ActivityRotation {

	private int index; // 索引
	private int itemId; // 物品编号
	private int num; // 数量
	private int probability; // 概率
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
	 * @return the itemId
	 */
	public int getItemId() {
		return itemId;
	}

	/**
	 * @param itemId
	 *            the itemId to set
	 */
	public void setItemId(int itemId) {
		this.itemId = itemId;
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
