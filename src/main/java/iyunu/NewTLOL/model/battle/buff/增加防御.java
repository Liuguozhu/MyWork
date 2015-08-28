package iyunu.NewTLOL.model.battle.buff;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;

public class 增加防御 implements IBuff {

	private 增加防御() {

	}

	private static 增加防御 instance = new 增加防御();

	public static 增加防御 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter character, BattleInfo battleInfo, BattleBuff battleBuff) {
		character.getPropertyBattle().addBuffPdefence((int) (character.getPropertyBattle().getOriginalPdefence() * battleBuff.getValue() / 10000f));
		character.getPropertyBattle().addBuffMdefence((int) (character.getPropertyBattle().getOriginalMdefence() * battleBuff.getValue() / 10000f));
		return false;
	}

}
