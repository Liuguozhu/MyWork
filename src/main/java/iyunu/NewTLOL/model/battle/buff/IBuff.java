package iyunu.NewTLOL.model.battle.buff;

import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;

public interface IBuff {

	boolean effect(BattleCharacter character, BattleInfo battleInfo, BattleBuff battleBuff);
}
