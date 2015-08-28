package iyunu.NewTLOL.model.partner;

public class Coefficient {

	/** 生命值系数 **/
	private int hp_coefficient;
	/** 攻击系数 **/
	private int attack_coefficient;
	/** 防御系数 **/
	private int defence_coefficient;
	/** 敏捷系数 **/
	private int speed_coefficient;

	public int getHp_coefficient() {
		return hp_coefficient;
	}

	public void setHp_coefficient(int hp_coefficient) {
		this.hp_coefficient = hp_coefficient;
	}

	public int getAttack_coefficient() {
		return attack_coefficient;
	}

	public void setAttack_coefficient(int attack_coefficient) {
		this.attack_coefficient = attack_coefficient;
	}

	public int getDefence_coefficient() {
		return defence_coefficient;
	}

	public void setDefence_coefficient(int defence_coefficient) {
		this.defence_coefficient = defence_coefficient;
	}

	public int getSpeed_coefficient() {
		return speed_coefficient;
	}

	public void setSpeed_coefficient(int speed_coefficient) {
		this.speed_coefficient = speed_coefficient;
	}

}
