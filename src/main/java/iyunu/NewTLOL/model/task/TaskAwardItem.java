package iyunu.NewTLOL.model.task;

/**
 * 任务物品奖励对象
 * 
 * @author sunhonglei
 * 
 */
public class TaskAwardItem {

	private int itemId; // 物品编号
	private int num; // 数量
	private int probability; // 概率
	private int isBind; // 绑定（0.不绑定，1.绑定）

	public TaskAwardItem(int itemId, int num, int probability, int isBind) {
		this.itemId = itemId;
		this.num = num;
		this.probability = probability;
		this.isBind = isBind;
	}

	public TaskAwardItem() {

	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getProbability() {
		return probability;
	}

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
