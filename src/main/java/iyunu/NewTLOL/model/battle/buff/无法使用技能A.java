package iyunu.NewTLOL.model.battle.buff;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;

public class 无法使用技能A implements IBuff {

	private 无法使用技能A() {

	}

	private static 无法使用技能A instance = new 无法使用技能A();

	public static 无法使用技能A instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter character, BattleInfo battleInfo, BattleBuff battleBuff) {
		return false;
	}

}
