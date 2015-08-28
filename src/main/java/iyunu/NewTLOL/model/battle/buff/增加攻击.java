package iyunu.NewTLOL.model.battle.buff;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;

public class 增加攻击 implements IBuff {

	private 增加攻击() {

	}

	private static 增加攻击 instance = new 增加攻击();

	public static 增加攻击 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter character, BattleInfo battleInfo, BattleBuff battleBuff) {
		character.getPropertyBattle().addBuffPattack((int) (character.getPropertyBattle().getOriginalPattack() * battleBuff.getValue() / 10000f));
		character.getPropertyBattle().addBuffMattack((int) (character.getPropertyBattle().getOriginalMattack() * battleBuff.getValue() / 10000f));
		return false;
	}

}
