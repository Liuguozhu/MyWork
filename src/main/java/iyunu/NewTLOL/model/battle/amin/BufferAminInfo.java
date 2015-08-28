package iyunu.NewTLOL.model.battle.amin;

/**
 * 战斗BUFF对象
 * 
 * @author SunHonglei
 * 
 */
public class BufferAminInfo {

	/** 索引 **/
	private int index;
	/** buff编号 **/
	private int buffId;
	/** 血量变化量（正数加血，负数减血） **/
	private int hp = 0;
	/** 是否死亡（1.是，0.否） **/
	private int isDead = 0;

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
	 * @return the buffId
	 */
	public int getBuffId() {
		return buffId;
	}

	/**
	 * @param buffId
	 *            the buffId to set
	 */
	public void setBuffId(int buffId) {
		this.buffId = buffId;
	}

	/**
	 * @return the hp
	 */
	public int getHp() {
		return hp;
	}

	/**
	 * @param hp
	 *            the hp to set
	 */
	public void setHp(int hp) {
		this.hp = hp;
	}

	/**
	 * @return the isDead
	 */
	public int getIsDead() {
		return isDead;
	}

	/**
	 * @param isDead
	 *            the isDead to set
	 */
	public void setIsDead(int isDead) {
		this.isDead = isDead;
	}

}
