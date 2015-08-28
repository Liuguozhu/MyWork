package iyunu.NewTLOL.model.item.res;

import iyunu.NewTLOL.enumeration.EFigure;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.item.instance.Equip;

/**
 * 装备资源类
 * 
 * @author SunHonglei
 * 
 */
public class EquipRes extends BaseItemRes {
	/** 部位 **/
	private EEquip part;
	private int pattack; // 外功攻击
	private int mattack; // 内功攻击
	private int pdefence; // 外功防御
	private int mdefence; // 内功防御
	private int hpMax; // 生命上限
	private int mpMax; // 内力上限
	private int speed; // 速度
	private int hit; // 命中
	private int dodge; // 闪避
	private int crit; // 暴击
	private int parry; // 格挡
	/** 使用形象 **/
	private EFigure figure;
	private int steps; // 阶
	private int resolve; // 分解获得升星石
	private int startRate; // 星系数

	public Item toItem() {
		Equip item = (Equip) super.toItem();
		item.setPart(part);
		item.setPattack(pattack);
		item.setMattack(mattack);
		item.setPdefence(pdefence);
		item.setMdefence(mdefence);
		item.setHpMax(hpMax);
		item.setMpMax(mpMax);
		item.setSpeed(speed);
		item.setHit(hit);
		item.setCrit(crit);
		item.setParry(parry);
		item.setDodge(dodge);
		item.setFigure(figure);
		item.setSteps(steps);
		item.setResolve(resolve);
		item.setStartRate(startRate);
		return item;
	}

	/**
	 * @return the part
	 */
	public EEquip getPart() {
		return part;
	}

	/**
	 * @param part
	 *            the part to set
	 */
	public void setPart(EEquip part) {
		this.part = part;
	}

	/**
	 * @return the pattack
	 */
	public int getPattack() {
		return pattack;
	}

	/**
	 * @param pattack
	 *            the pattack to set
	 */
	public void setPattack(int pattack) {
		this.pattack = pattack;
	}

	/**
	 * @return the mattack
	 */
	public int getMattack() {
		return mattack;
	}

	/**
	 * @param mattack
	 *            the mattack to set
	 */
	public void setMattack(int mattack) {
		this.mattack = mattack;
	}

	/**
	 * @return the pdefence
	 */
	public int getPdefence() {
		return pdefence;
	}

	/**
	 * @param pdefence
	 *            the pdefence to set
	 */
	public void setPdefence(int pdefence) {
		this.pdefence = pdefence;
	}

	/**
	 * @return the mdefence
	 */
	public int getMdefence() {
		return mdefence;
	}

	/**
	 * @param mdefence
	 *            the mdefence to set
	 */
	public void setMdefence(int mdefence) {
		this.mdefence = mdefence;
	}

	/**
	 * @return the hpMax
	 */
	public int getHpMax() {
		return hpMax;
	}

	/**
	 * @param hpMax
	 *            the hpMax to set
	 */
	public void setHpMax(int hpMax) {
		this.hpMax = hpMax;
	}

	/**
	 * @return the mpMax
	 */
	public int getMpMax() {
		return mpMax;
	}

	/**
	 * @param mpMax
	 *            the mpMax to set
	 */
	public void setMpMax(int mpMax) {
		this.mpMax = mpMax;
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
	 * @return the hit
	 */
	public int getHit() {
		return hit;
	}

	/**
	 * @param hit
	 *            the hit to set
	 */
	public void setHit(int hit) {
		this.hit = hit;
	}

	/**
	 * @return the dodge
	 */
	public int getDodge() {
		return dodge;
	}

	/**
	 * @param dodge
	 *            the dodge to set
	 */
	public void setDodge(int dodge) {
		this.dodge = dodge;
	}

	/**
	 * @return the crit
	 */
	public int getCrit() {
		return crit;
	}

	/**
	 * @param crit
	 *            the crit to set
	 */
	public void setCrit(int crit) {
		this.crit = crit;
	}

	/**
	 * @return the parry
	 */
	public int getParry() {
		return parry;
	}

	/**
	 * @param parry
	 *            the parry to set
	 */
	public void setParry(int parry) {
		this.parry = parry;
	}

	/**
	 * @return the figure
	 */
	public EFigure getFigure() {
		return figure;
	}

	/**
	 * @param figure
	 *            the figure to set
	 */
	public void setFigure(EFigure figure) {
		this.figure = figure;
	}

	/**
	 * @return the steps
	 */
	public int getSteps() {
		return steps;
	}

	/**
	 * @param steps
	 *            the steps to set
	 */
	public void setSteps(int steps) {
		this.steps = steps;
	}

	/**
	 * @return the resolve
	 */
	public int getResolve() {
		return resolve;
	}

	/**
	 * @param resolve
	 *            the resolve to set
	 */
	public void setResolve(int resolve) {
		this.resolve = resolve;
	}

	/**
	 * @return the startRate
	 */
	public int getStartRate() {
		return startRate;
	}

	/**
	 * @param startRate
	 *            the startRate to set
	 */
	public void setStartRate(int startRate) {
		this.startRate = startRate;
	}

}
