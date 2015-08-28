package iyunu.NewTLOL.model.battle.buff;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;

public class 附加持续掉血 implements IBuff {

	private 附加持续掉血() {

	}

	private static 附加持续掉血 instance = new 附加持续掉血();

	public static 附加持续掉血 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter character, BattleInfo battleInfo, BattleBuff battleBuff) {
		return false;
	}

}
