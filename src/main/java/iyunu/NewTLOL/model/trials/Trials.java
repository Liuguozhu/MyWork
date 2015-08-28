package iyunu.NewTLOL.model.trials;

/**
 * 试练信息对象
 * 
 * @author sunhonglei
 * 
 */
public class Trials {

	private int id; // 编号
	private int degree; // 难度
	private int position; // 位置
	private int state; // 状态（0.未通关，1.通关）

	public Trials() {

	}

	public Trials(int id, int degree) {
		this.id = id;
		this.degree = degree;
	}

	public void clear() {
		position = 0;
	}

	public boolean increasePosition() {
		position++;
		if (state == 0 && position == 8) {
			state = 1;
			return true;
		}
		return false;
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
	 * @return the degree
	 */
	public int getDegree() {
		return degree;
	}

	/**
	 * @param degree
	 *            the degree to set
	 */
	public void setDegree(int degree) {
		this.degree = degree;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

}
