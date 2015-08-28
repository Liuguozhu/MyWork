package iyunu.NewTLOL.model.item.instance;

import iyunu.NewTLOL.enumeration.EFigure;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.item.AddProperty;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.item.EquipServer;

import java.util.ArrayList;
import java.util.Map;

/**
 * 装备实例类
 * 
 * @author SunHonglei
 * 
 */
public class Equip extends Item {

	/** 部位 **/
	private EEquip part;
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
	/** 速度 **/
	private int speed;
	private int hit; // 命中
	private int dodge; // 闪避
	private int crit; // 暴击
	private int parry; // 格挡
	/** 使用形象 **/
	private EFigure figure;
	/** 分解获得升星石 **/
	private int resolve;
	/** 阶 **/
	private int steps;
	/** 星 **/
	private int star = 0; // 存数据库
	/** 幸运值 **/
	private int luck; // 存数据库
	/** 幸运值上限 **/
	private int luckLimit; // 存数据库
	/** 增加属性百分比 **/
	private int propertyPercent; // 存数据库
	/** 附加属性 **/
	private ArrayList<AddProperty> addPropertyList = new ArrayList<AddProperty>(); // 存数据库
	private int startRate;

	public int totalValue() {
		return EquipServer.countPower(this);
	}

	@Override
	public void change() {
		// TODO Auto-generated method stub
	}

	@SuppressWarnings("unchecked")
	public Equip copy() {
		Equip item = new Equip();
		Item.init(item, this);

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
		item.setStar(star);
		item.setLuck(luck);
		item.setLuckLimit(luckLimit);
		item.setResolve(resolve);
		item.setPropertyPercent(propertyPercent);
		item.setAddPropertyList((ArrayList<AddProperty>) addPropertyList.clone());
		item.setStartRate(startRate);
		return item;
	}

	public boolean use(Role role, int num, Map<Integer, Cell> cellsMap) {
		return false;
	}

	@Override
	public boolean use(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo) {
		return false;
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

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
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
	 * @return the addPropertyList
	 */
	public ArrayList<AddProperty> getAddPropertyList() {
		return addPropertyList;
	}

	/**
	 * @param addPropertyList
	 *            the addPropertyList to set
	 */
	public void setAddPropertyList(ArrayList<AddProperty> addPropertyList) {
		this.addPropertyList = addPropertyList;
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
	 * @return the star
	 */
	public int getStar() {
		return star;
	}

	/**
	 * @param star
	 *            the star to set
	 */
	public void setStar(int star) {
		this.star = star;
	}

	public int getLuck() {
		return luck;
	}

	public void setLuck(int luck) {
		this.luck = luck;
	}

	public int getLuckLimit() {
		return luckLimit;
	}

	public void setLuckLimit(int luckLimit) {
		this.luckLimit = luckLimit;
	}

	public int getPropertyPercent() {
		return propertyPercent;
	}

	public void setPropertyPercent(int propertyPercent) {
		this.propertyPercent = propertyPercent;
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
