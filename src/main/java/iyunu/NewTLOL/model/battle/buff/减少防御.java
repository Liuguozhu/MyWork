package iyunu.NewTLOL.model.battle.buff;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;

public class 减少防御 implements IBuff {

	private 减少防御() {

	}

	private static 减少防御 instance = new 减少防御();

	public static 减少防御 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter character, BattleInfo battleInfo, BattleBuff battleBuff) {
		character.getPropertyBattle().addBuffPdefence(-1 * (int) (character.getPropertyBattle().getOriginalPdefence() * battleBuff.getValue() / 10000f));
		character.getPropertyBattle().addBuffMdefence(-1 *(int) (character.getPropertyBattle().getOriginalMdefence() * battleBuff.getValue() / 10000f));
		return false;
	}

}
