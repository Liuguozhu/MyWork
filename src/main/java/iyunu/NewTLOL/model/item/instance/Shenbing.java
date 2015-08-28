package iyunu.NewTLOL.model.item.instance;

import iyunu.NewTLOL.enumeration.EFigure;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;

import java.util.Map;

public class Shenbing extends Item {

	/** 阶 **/
	private int steps;
	/** 星级 **/
	private int start;
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
	/** 使用形象 **/
	private EFigure figure;

	/** 神兵附加属性 **/
	private int addMattack;
	private int addPattack;
	private int addMdefence;
	private int addPdefence;
	private int addHp;
	private int addMp;
	private int addSpeed;

	@Override
	public Shenbing copy() {
		Shenbing item = new Shenbing();
		Item.init(item, this);

		item.setSteps(steps);
		item.setStart(start);
		item.setPart(part);
		item.setPattack(pattack);
		item.setMattack(mattack);
		item.setPdefence(pdefence);
		item.setMdefence(mdefence);
		item.setHpMax(hpMax);
		item.setMpMax(mpMax);
		item.setSpeed(speed);
		item.setFigure(figure);
		
		item.setAddMattack(addMattack);
		item.setAddPattack(addPattack);
		item.setAddMdefence(addMdefence);
		item.setAddPdefence(addPdefence);
		item.setAddHp(addHp);
		item.setAddMp(addMp);
		item.setAddSpeed(addSpeed);

		return item;
	}

	@Override
	public boolean use(Role role, int num, Map<Integer, Cell> cellsMap) {
		return false;
	}

	@Override
	public void change() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean use(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo) {
		return false;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public EEquip getPart() {
		return part;
	}

	public void setPart(EEquip part) {
		this.part = part;
	}

	public int getHpMax() {
		return hpMax;
	}

	public void setHpMax(int hpMax) {
		this.hpMax = hpMax;
	}

	public int getMpMax() {
		return mpMax;
	}

	public void setMpMax(int mpMax) {
		this.mpMax = mpMax;
	}

	public int getPattack() {
		return pattack;
	}

	public void setPattack(int pattack) {
		this.pattack = pattack;
	}

	public int getPdefence() {
		return pdefence;
	}

	public void setPdefence(int pdefence) {
		this.pdefence = pdefence;
	}

	public int getMattack() {
		return mattack;
	}

	public void setMattack(int mattack) {
		this.mattack = mattack;
	}

	public int getMdefence() {
		return mdefence;
	}

	public void setMdefence(int mdefence) {
		this.mdefence = mdefence;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public EFigure getFigure() {
		return figure;
	}

	public void setFigure(EFigure figure) {
		this.figure = figure;
	}

	public int getAddMattack() {
		return addMattack;
	}

	public void setAddMattack(int addMattack) {
		this.addMattack = addMattack;
	}

	public int getAddPattack() {
		return addPattack;
	}

	public void setAddPattack(int addPattack) {
		this.addPattack = addPattack;
	}

	public int getAddMdefence() {
		return addMdefence;
	}

	public void setAddMdefence(int addMdefence) {
		this.addMdefence = addMdefence;
	}

	public int getAddPdefence() {
		return addPdefence;
	}

	public void setAddPdefence(int addPdefence) {
		this.addPdefence = addPdefence;
	}

	public int getAddHp() {
		return addHp;
	}

	public void setAddHp(int addHp) {
		this.addHp = addHp;
	}

	public int getAddMp() {
		return addMp;
	}

	public void setAddMp(int addMp) {
		this.addMp = addMp;
	}

	public int getAddSpeed() {
		return addSpeed;
	}

	public void setAddSpeed(int addSpeed) {
		this.addSpeed = addSpeed;
	}

}
