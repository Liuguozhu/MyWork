package iyunu.NewTLOL.model.battle.buff;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.battle.EBattleBuffType;
import iyunu.NewTLOL.model.battle.amin.BufferAminInfo;

public class 再生 implements IBuff {

	private 再生() {

	}

	private static 再生 instance = new 再生();

	public static 再生 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter character, BattleInfo battleInfo, BattleBuff battleBuff) {
		int hp = character.getPropertyBattle().getHpMax() - character.getPropertyBattle().getHp();
		if (hp > 0) {

			BufferAminInfo bufferAminInfo = new BufferAminInfo(); // BUFF动画——创建对象
			battleInfo.addBuffInfo(EBattleBuffType.b16, bufferAminInfo); // BUFF动画——保存在战斗信息中
			bufferAminInfo.setIndex(character.getIndex()); // BUFF动画——记录索引
			bufferAminInfo.setBuffId(battleBuff.getId()); // BUFF动画——记录buff编号

			if (battleBuff.getValue() <= hp) {
				character.getPropertyBattle().addHp(battleBuff.getValue());
				bufferAminInfo.setHp(battleBuff.getValue()); // BUFF动画——防守者——记录血量变化
			} else {
				character.getPropertyBattle().addHp(hp);
				bufferAminInfo.setHp(hp); // BUFF动画——防守者——记录血量变化
			}
		}

		return false;
	}

}
