package iyunu.NewTLOL.model.battle.buff;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.battle.EBattleBuffType;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class 净化 implements IBuff {

	private 净化() {

	}

	private static 净化 instance = new 净化();

	public static 净化 instance() {
		return instance;
	}

	@Override
	public boolean effect(BattleCharacter character, BattleInfo battleInfo, BattleBuff battleBuff) {

		Set<Entry<EBattleBuffType, BattleBuff>> set = character.getBuffs().entrySet();
		for (Iterator<Entry<EBattleBuffType, BattleBuff>> it = set.iterator(); it.hasNext();) {
			Entry<EBattleBuffType, BattleBuff> entry = it.next();
			if (entry.getValue().getDebuff().equals(EBattleBuffEffect.debuff)) {
				character.getDisappearBuffs().add(entry.getValue().getId());
//				it.remove();
			}
		}

		return false;
	}

}
