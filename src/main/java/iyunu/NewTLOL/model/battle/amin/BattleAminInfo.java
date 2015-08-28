package iyunu.NewTLOL.model.battle.amin;

import iyunu.NewTLOL.model.battle.BattleCharacter;

import java.util.ArrayList;
import java.util.List;

public class BattleAminInfo {

	private BattleAmin attackerAmin; // 攻击者
	private List<BattleAmin> defenderAmins; // 被攻击者集合
	private BattleCharacter member; // 伙伴

	/**
	 * 构造方法
	 */
	public BattleAminInfo() {
		attackerAmin = new BattleAmin();
		defenderAmins = new ArrayList<BattleAmin>();
	}

	/**
	 * @return the attackerAmin
	 */
	public BattleAmin getAttackerAmin() {
		return attackerAmin;
	}

	/**
	 * @return the defenderAmins
	 */
	public List<BattleAmin> getDefenderAmins() {
		return defenderAmins;
	}

	/**
	 * @return the member
	 */
	public BattleCharacter getMember() {
		return member;
	}

	/**
	 * @param member
	 *            the member to set
	 */
	public void setMember(BattleCharacter member) {
		this.member = member;
	}

}