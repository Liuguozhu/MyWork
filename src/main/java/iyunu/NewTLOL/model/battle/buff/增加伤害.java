package iyunu.NewTLOL.model.battle.buff;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;

public class 增加伤害 implements IBuff {

	private 增加伤害() {

	}

	private static 增加伤害 instance = new 增加伤害();

	public static 增加伤害 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter character, BattleInfo battleInfo, BattleBuff battleBuff) {
		character.getPropertyBattle().addBuffPharm(battleBuff.getValue());
		character.getPropertyBattle().addBuffMharm(battleBuff.getValue());
		return false;
	}

}
