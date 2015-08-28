package iyunu.NewTLOL.model.battle.buff;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.battle.EBattleBuffType;
import iyunu.NewTLOL.model.battle.amin.BufferAminInfo;

public class 持续掉血 implements IBuff {

	private 持续掉血() {

	}

	private static 持续掉血 instance = new 持续掉血();

	public static 持续掉血 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter character, BattleInfo battleInfo, BattleBuff battleBuff) {
		int hp = (int) (character.getPropertyBattle().getHp() * (battleBuff.getValue() / 10000f));
		hp = hp < 1 ? 1 : hp;

		BufferAminInfo bufferAminInfo = new BufferAminInfo(); // BUFF动画——创建对象
		battleInfo.addBuffInfo(EBattleBuffType.b6, bufferAminInfo); // BUFF动画——保存在战斗信息中
		bufferAminInfo.setIndex(character.getIndex()); // BUFF动画——记录索引
		bufferAminInfo.setBuffId(battleBuff.getId()); // BUFF动画——记录buff编号

		if (character.getPropertyBattle().getHp() > hp) {

			bufferAminInfo.setHp(hp * -1); // BUFF动画——防守者——记录血量变化

			character.getPropertyBattle().setHp(character.getPropertyBattle().getHp() - hp);
			bufferAminInfo.setIsDead(0);
			return false;
		} else {

			bufferAminInfo.setHp(character.getPropertyBattle().getHp() * -1); // BUFF动画——防守者——记录血量变化

			character.getPropertyBattle().setHp(0);
			bufferAminInfo.setIsDead(1);
			character.setDead(true);
			return true;
		}

	}

}
