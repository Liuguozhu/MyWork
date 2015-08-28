package iyunu.NewTLOL.model.battle.buff;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;

public class 增加免伤 implements IBuff {

	private 增加免伤() {

	}

	private static 增加免伤 instance = new 增加免伤();

	public static 增加免伤 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter character, BattleInfo battleInfo, BattleBuff battleBuff) {
		return false;
	}

}
