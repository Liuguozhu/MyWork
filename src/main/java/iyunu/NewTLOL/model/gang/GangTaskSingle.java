package iyunu.NewTLOL.model.gang;

public class GangTaskSingle {
	private int index;
	/** 0未领取 1已领取 */
	private int finish = 0;

	public GangTaskSingle() {

	}

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
	 * @return the finish
	 */
	public int getFinish() {
		return finish;
	}

	/**
	 * @param finish
	 *            the finish to set
	 */
	public void setFinish(int finish) {
		this.finish = finish;
	}

}
