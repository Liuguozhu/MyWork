package iyunu.NewTLOL.model.battle.buff;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;

public class 增加内功防御 implements IBuff {

	private 增加内功防御() {

	}

	private static 增加内功防御 instance = new 增加内功防御();

	public static 增加内功防御 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter character, BattleInfo battleInfo, BattleBuff battleBuff) {
		character.getPropertyBattle().addBuffMdefence(battleBuff.getValue());
		return false;
	}

}
