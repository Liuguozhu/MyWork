package iyunu.NewTLOL.model.battle.buff;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;

public class 减少内功防御 implements IBuff {

	private 减少内功防御() {

	}

	private static 减少内功防御 instance = new 减少内功防御();

	public static 减少内功防御 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter character, BattleInfo battleInfo, BattleBuff battleBuff) {
		character.getPropertyBattle().addBuffMdefence(-1 * battleBuff.getValue());
		return false;
	}

}
