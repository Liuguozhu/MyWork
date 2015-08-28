package iyunu.NewTLOL.model.battle.buff;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;

public class 减少攻击 implements IBuff {

	private 减少攻击() {

	}

	private static 减少攻击 instance = new 减少攻击();

	public static 减少攻击 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter character, BattleInfo battleInfo, BattleBuff battleBuff) {
		character.getPropertyBattle().addBuffPattack(-1 * (int) (character.getPropertyBattle().getOriginalPattack() * battleBuff.getValue() / 10000f));
		character.getPropertyBattle().addBuffMattack(-1 *(int) (character.getPropertyBattle().getOriginalMattack() * battleBuff.getValue() / 10000f));
		return false;
	}

}
