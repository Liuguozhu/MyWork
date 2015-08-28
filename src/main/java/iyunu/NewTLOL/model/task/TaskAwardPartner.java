package iyunu.NewTLOL.model.task;

/**
 * @function 任务奖励伙伴对象
 * @author LuoSR
 * @date 2014年9月15日
 */
public class TaskAwardPartner {

	private int index; // 伙伴索引
	private int num; // 数量
	private int probability; // 概率
	private int isBind; // 绑定（0.不绑定，1.绑定）

	public TaskAwardPartner(int index, int num, int probability, int isBind) {
		this.index = index;
		this.num = num;
		this.probability = probability;
		this.isBind = isBind;
	}

	public TaskAwardPartner() {
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
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
