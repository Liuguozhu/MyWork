package iyunu.NewTLOL.model.battle.buff;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;

public class 增加内伤 implements IBuff {

	private 增加内伤() {

	}

	private static 增加内伤 instance = new 增加内伤();

	public static 增加内伤 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter character, BattleInfo battleInfo, BattleBuff battleBuff) {
		character.getPropertyBattle().addBuffMharm(battleBuff.getValue());
		return false;
	}

}
