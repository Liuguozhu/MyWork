package iyunu.NewTLOL.model.map;

/**
 * 明怪位置
 * 
 * @author SunHonglei
 * 
 */
public class DistributionOvert {

	private long uid; // 唯一编号
	private int x; // x坐标
	private int y; // y坐标
	private int monsterGroup; // 怪物组合

	/**
	 * @return the uid
	 */
	public long getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(long uid) {
		this.uid = uid;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the monsterGroup
	 */
	public int getMonsterGroup() {
		return monsterGroup;
	}

	/**
	 * @param monsterGroup
	 *            the monsterGroup to set
	 */
	public void setMonsterGroup(int monsterGroup) {
		this.monsterGroup = monsterGroup;
	}

}
