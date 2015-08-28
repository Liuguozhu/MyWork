package iyunu.NewTLOL.model.battle.buff;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;

public class 增加闪避 implements IBuff {

	private 增加闪避() {

	}

	private static 增加闪避 instance = new 增加闪避();

	public static 增加闪避 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter character, BattleInfo battleInfo, BattleBuff battleBuff) {
		character.getPropertyBattle().addBuffDodge(battleBuff.getValue());
		return false;
	}

}
