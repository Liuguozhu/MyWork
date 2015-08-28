package iyunu.NewTLOL.model.battle.buff;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;

public class 无法使用技能B implements IBuff {

	private 无法使用技能B() {

	}

	private static 无法使用技能B instance = new 无法使用技能B();

	public static 无法使用技能B instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter character, BattleInfo battleInfo, BattleBuff battleBuff) {
		return false;
	}

}
