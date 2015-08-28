package iyunu.NewTLOL.model.intensify.res;

import iyunu.NewTLOL.model.intensify.instance.BodyInfo;
import iyunu.NewTLOL.model.item.EEquip;

public class BodyInfoRes {
	private int level;
	private EEquip part;
	private int mattack;
	private int pattack;
	private int mdefence;
	private int pdefence;
	private int hp;
	private int mp;
	private int speed;
	private int gold;
	private int vipCost;

	private int addMattack;
	private int addPattack;
	private int addMdefence;
	private int addPdefence;
	private int addHp;
	private int addMp;
	private int addSpeed;

	public BodyInfo toEquipIntensify() {
		BodyInfo equipIntensify = new BodyInfo();
		equipIntensify.setLevel(level);
		equipIntensify.setPart(part);
		equipIntensify.setMattack(mattack);
		equipIntensify.setPattack(pattack);
		equipIntensify.setMdefence(mdefence);
		equipIntensify.setPdefence(pdefence);
		equipIntensify.setHp(hp);
		equipIntensify.setMp(mp);
		equipIntensify.setSpeed(speed);
		equipIntensify.setGold(gold);
		equipIntensify.setVipCost(vipCost);
		equipIntensify.setAddMattack(addMattack);
		equipIntensify.setAddPattack(addPattack);
		equipIntensify.setAddMdefence(addMdefence);
		equipIntensify.setAddPdefence(addPdefence);
		equipIntensify.setAddHp(addHp);
		equipIntensify.setAddMp(addMp);
		equipIntensify.setAddSpeed(addSpeed);
		return equipIntensify;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public EEquip getPart() {
		return part;
	}

	public void setPart(EEquip part) {
		this.part = part;
	}

	public int getMattack() {
		return mattack;
	}

	public void setMattack(int mattack) {
		this.mattack = mattack;
	}

	public int getPattack() {
		return pattack;
	}

	public void setPattack(int pattack) {
		this.pattack = pattack;
	}

	public int getMdefence() {
		return mdefence;
	}

	public void setMdefence(int mdefence) {
		this.mdefence = mdefence;
	}

	public int getPdefence() {
		return pdefence;
	}

	public void setPdefence(int pdefence) {
		this.pdefence = pdefence;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getMp() {
		return mp;
	}

	public void setMp(int mp) {
		this.mp = mp;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	/**
	 * @return the vipCost
	 */
	public int getVipCost() {
		return vipCost;
	}

	/**
	 * @param vipCost
	 *            the vipCost to set
	 */
	public void setVipCost(int vipCost) {
		this.vipCost = vipCost;
	}

	/**
	 * @return the speed
	 */
	public int getSpeed() {
		return speed;
	}

	/**
	 * @param speed
	 *            the speed to set
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * @return the addMattack
	 */
	public int getAddMattack() {
		return addMattack;
	}

	/**
	 * @param addMattack
	 *            the addMattack to set
	 */
	public void setAddMattack(int addMattack) {
		this.addMattack = addMattack;
	}

	/**
	 * @return the addPattack
	 */
	public int getAddPattack() {
		return addPattack;
	}

	/**
	 * @param addPattack
	 *            the addPattack to set
	 */
	public void setAddPattack(int addPattack) {
		this.addPattack = addPattack;
	}

	/**
	 * @return the addMdefence
	 */
	public int getAddMdefence() {
		return addMdefence;
	}

	/**
	 * @param addMdefence
	 *            the addMdefence to set
	 */
	public void setAddMdefence(int addMdefence) {
		this.addMdefence = addMdefence;
	}

	/**
	 * @return the addPdefence
	 */
	public int getAddPdefence() {
		return addPdefence;
	}

	/**
	 * @param addPdefence
	 *            the addPdefence to set
	 */
	public void setAddPdefence(int addPdefence) {
		this.addPdefence = addPdefence;
	}

	/**
	 * @return the addHp
	 */
	public int getAddHp() {
		return addHp;
	}

	/**
	 * @param addHp
	 *            the addHp to set
	 */
	public void setAddHp(int addHp) {
		this.addHp = addHp;
	}

	/**
	 * @return the addMp
	 */
	public int getAddMp() {
		return addMp;
	}

	/**
	 * @param addMp
	 *            the addMp to set
	 */
	public void setAddMp(int addMp) {
		this.addMp = addMp;
	}

	/**
	 * @return the addSpeed
	 */
	public int getAddSpeed() {
		return addSpeed;
	}

	/**
	 * @param addSpeed
	 *            the addSpeed to set
	 */
	public void setAddSpeed(int addSpeed) {
		this.addSpeed = addSpeed;
	}

}
