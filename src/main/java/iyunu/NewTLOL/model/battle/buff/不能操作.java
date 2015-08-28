package iyunu.NewTLOL.model.battle.buff;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;

public class 不能操作 implements IBuff {

	private 不能操作() {

	}

	private static 不能操作 instance = new 不能操作();

	public static 不能操作 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter character, BattleInfo battleInfo, BattleBuff battleBuff) {
		return false;
	}

}
