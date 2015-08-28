package iyunu.NewTLOL.model.battle.buff;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;

public class 增加暴击 implements IBuff {

	private 增加暴击() {

	}

	private static 增加暴击 instance = new 增加暴击();

	public static 增加暴击 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter character, BattleInfo battleInfo, BattleBuff battleBuff) {
		character.getPropertyBattle().addBuffCrit(battleBuff.getValue());
		return false;
	}

}
