package iyunu.NewTLOL.model.battle.buff;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;

public class 增加外伤 implements IBuff {

	private 增加外伤() {

	}

	private static 增加外伤 instance = new 增加外伤();

	public static 增加外伤 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter character, BattleInfo battleInfo, BattleBuff battleBuff) {
		character.getPropertyBattle().addBuffPharm(battleBuff.getValue());
		return false;
	}

}
