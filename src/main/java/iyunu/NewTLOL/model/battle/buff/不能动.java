package iyunu.NewTLOL.model.battle.buff;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;

public class 不能动 implements IBuff {

	private 不能动() {

	}

	private static 不能动 instance = new 不能动();

	public static 不能动 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter character, BattleInfo battleInfo, BattleBuff battleBuff) {
		return false;
	}

}
