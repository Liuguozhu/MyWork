package iyunu.NewTLOL.model.blood;

import iyunu.NewTLOL.enumeration.Vocation;

public class Blood {
	private long id;
	private int level;
	private String nick;
	private int mark;
	private Vocation vocation;
	private int blood;
	private int multiKill;
	private int killNum;
	private int dead;
	private int hkill;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick
	 *            the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the mark
	 */
	public int getMark() {
		return mark;
	}

	/**
	 * @param mark
	 *            the mark to set
	 */
	public void setMark(int mark) {
		this.mark = mark;
	}

	/**
	 * @return the vocation
	 */
	public Vocation getVocation() {
		return vocation;
	}

	/**
	 * @param vocation
	 *            the vocation to set
	 */
	public void setVocation(Vocation vocation) {
		this.vocation = vocation;
	}

	/**
	 * @return the blood
	 */
	public int getBlood() {
		return blood;
	}

	/**
	 * @param blood
	 *            the blood to set
	 */
	public void setBlood(int blood) {
		this.blood = blood;
	}

	/**
	 * @return the multiKill
	 */
	public int getMultiKill() {
		return multiKill;
	}

	/**
	 * @param multiKill
	 *            the multiKill to set
	 */
	public void setMultiKill(int multiKill) {
		this.multiKill = multiKill;
	}

	public int getKillNum() {
		return killNum;
	}

	public void setKillNum(int killNum) {
		this.killNum = killNum;
	}

	public int getDead() {
		return dead;
	}

	public void setDead(int dead) {
		this.dead = dead;
	}

	public int getHkill() {
		return hkill;
	}

	public void setHkill(int hkill) {
		this.hkill = hkill;
	}

}
