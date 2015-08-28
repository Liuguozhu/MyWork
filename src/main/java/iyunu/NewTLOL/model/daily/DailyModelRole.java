package iyunu.NewTLOL.model.daily;

public class DailyModelRole {
	private int id;
	private int count = 0;// 完成了多少
	private int target = 1;// 目标
	private int rec = 0;// 0不可领取领取 1可领取2已领取

	// private boolean finish;// 0 未完成 1完成

	public DailyModelRole() {

	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}

	/**
	 * @return the target
	 */
	public int getTarget() {
		return target;
	}

	/**
	 * @param target
	 *            the target to set
	 */
	public void setTarget(int target) {
		this.target = target;
	}

	/**
	 * @return the rec
	 */
	public int getRec() {
		return rec;
	}

	/**
	 * @param rec
	 *            the rec to set
	 */
	public void setRec(int rec) {
		this.rec = rec;
	}

	// /**
	// * @return the finish
	// */
	// public boolean isFinish() {
	// return finish;
	// }
	//
	// /**
	// * @param finish
	// * the finish to set
	// */
	// public void setFinish(boolean finish) {
	// this.finish = finish;
	// }

}
