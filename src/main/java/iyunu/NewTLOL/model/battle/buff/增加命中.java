package iyunu.NewTLOL.model.battle.buff;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;

public class 增加命中 implements IBuff {

	private 增加命中() {

	}

	private static 增加命中 instance = new 增加命中();

	public static 增加命中 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter character, BattleInfo battleInfo, BattleBuff battleBuff) {
		character.getPropertyBattle().addBuffHit(battleBuff.getValue());
		return false;
	}

}
