package iyunu.NewTLOL.model.item.instance;

import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;

import java.util.Map;

/**
 * 宝石实例类
 * 
 * @author SunHonglei
 * 
 */
public class Stone extends Item {

	private int species; // 种类
	/** 血上限 **/
	private int hpMax;
	/** 蓝上限 **/
	private int mpMax;
	/** 外功攻击 **/
	private int pattack;
	/** 外功防御 **/
	private int pdefence;
	/** 内功攻击 **/
	private int mattack;
	/** 内功防御 **/
	private int mdefence;
	/** 命中 **/
	private int hit;
	/** 闪避 **/
	private int dodge;
	/** 暴击 **/
	private int crit;
	/** 格挡 **/
	private int parry;
	/** 速度 **/
	private int speed;

	@Override
	public Stone copy() {
		Stone item = new Stone();
		Item.init(item, this);
		
		item.setSpecies(species);
		item.setPattack(pattack);
		item.setMattack(mattack);
		item.setPdefence(pdefence);
		item.setMdefence(mdefence);
		item.setHpMax(hpMax);
		item.setMpMax(mpMax);
		item.setParry(parry);
		item.setCrit(crit);
		item.setHit(hit);
		item.setDodge(dodge);
		item.setSpeed(speed);
		return item;
	}

	@Override
	public void change() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean use(Role role, int num, Map<Integer, Cell> cellsMap) {
		return false;
	}

	@Override
	public boolean use(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo) {
		return false;
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
	 * @return the species
	 */
	public int getSpecies() {
		return species;
	}

	/**
	 * @param species
	 *            the species to set
	 */
	public void setSpecies(int species) {
		this.species = species;
	}

}
