package iyunu.NewTLOL.model.partner.msg;

public class RateMsg {

	private int probability; // 概率
	private int min; // 下限
	private int max; // 上限

	public RateMsg(int probability, int min, int max) {
		this.probability = probability;
		this.min = min;
		this.max = max;
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
	 * @return the min
	 */
	public int getMin() {
		return min;
	}

	/**
	 * @param min
	 *            the min to set
	 */
	public void setMin(int min) {
		this.min = min;
	}

	/**
	 * @return the max
	 */
	public int getMax() {
		return max;
	}

	/**
	 * @param max
	 *            the max to set
	 */
	public void setMax(int max) {
		this.max = max;
	}

}
