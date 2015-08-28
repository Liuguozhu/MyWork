package iyunu.NewTLOL.server.battle;

import iyunu.NewTLOL.manager.BattleManager;
import iyunu.NewTLOL.message.FightMessage;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.battle.EBattleReuslt;
import iyunu.NewTLOL.model.battle.EDirection;
import iyunu.NewTLOL.model.ifce.ICharacter;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.team.Team;

import java.util.Iterator;
import java.util.Map.Entry;

public class FightServer {

	/**
	 * 战斗预处理
	 * 
	 * @param role
	 *            角色对象
	 * @param monsterGroup
	 *            怪物组合
	 * @param type
	 *            战斗类型
	 * @return 战斗对象
	 */
	public static BattleInfo preBattle(Role attacker, Role defender, BaseMap baseMap, boolean isAuto) {

		int size = 0;
		int leftNum = 0;
		int rightNum = 0;
		if (attacker.getTeam() != null) {
			size += attacker.getTeam().getMemberSize();
			leftNum = attacker.getTeam().getMemberSize();
		} else {
			size += 1;
			leftNum = 1;
		}
		if (defender.getTeam() != null) {
			size += defender.getTeam().getMemberSize();
			rightNum = defender.getTeam().getMemberSize();
		} else {
			size += 1;
			rightNum = 1;
		}

		int type = 4;
		if (isAuto) {
			type = 0;
		}
		BattleInfo battleInfo = new BattleInfo(size, type, isAuto);
		battleInfo.setLeftNum(leftNum);
		battleInfo.setRightNum(rightNum);

		// ==================填充右边队伍信息==================
		if (attacker.getTeam() == null) { // ======单人不组队======
			BattleCharacter battleCharacter = FightServer.changeBattleCharacter(7, attacker, EDirection.right);
			battleInfo.addCharacterInToRight(7, battleCharacter); // 玩家
			
			Iterator<Entry<Integer, Long>> it = attacker.getPartnerFight().entrySet().iterator();
			while (it.hasNext()) {
				Entry<Integer, Long> entry = it.next();
				if (entry.getValue() != 0) {
					Partner partner = attacker.findPartner(entry.getValue());
					if (partner != null) {
						battleCharacter.addPartner(entry.getValue()); // 添加出战伙伴
						int site= BattleServer.partnerRightSite(entry.getKey());
						battleInfo.addCharacterInToRight(site, BattleServer.changeBattleCharacter(site, partner, EDirection.right)); // 伙伴
					}
				}
			}
			
//			Long partnerId = attacker.getPartnerFight().get(1);
//			if (partnerId != null && partnerId != 0) {
//				Partner partner = attacker.findPartner(partnerId);
//				if (partner != null) {
//					battleCharacter.addPartner(partnerId); // 添加出战伙伴
//					battleInfo.addCharacterInToRight(10, FightServer.changeBattleCharacter(10, partner, EDirection.right)); // 伙伴
//				}
//			}
			battleInfo.setLeftLeader(attacker);
		} else {
			Team team = attacker.getTeam();
			for (int i = 0; i < team.getMember().size(); i++) {
				Role member = team.getMember().get(i);
				BattleCharacter battleCharacter = FightServer.changeBattleCharacter(7 + i, member, EDirection.right);
				battleInfo.addCharacterInToRight(7 + i, battleCharacter); // 玩家
				Long partnerId = member.getPartnerFight().get(1);
				if (partnerId != null && partnerId != 0) {
					Partner partner = member.findPartner(partnerId);
					if (partner != null) {
						battleCharacter.addPartner(partnerId); // 添加出战伙伴
						battleInfo.addCharacterInToRight(10 + i, FightServer.changeBattleCharacter(10 + i, partner, EDirection.right)); // 伙伴
					}
				}
				if (i == 0) {
					battleInfo.setLeftLeader(member);
				}
			}

		}

		// ========================填充左边队伍信息========================
		if (defender.getTeam() == null) { // ======单人不组队======
			BattleCharacter battleCharacter = FightServer.changeBattleCharacter(1, defender, EDirection.left);
			battleInfo.addCharacterInToLeft(1, battleCharacter); // 玩家
			
			Iterator<Entry<Integer, Long>> it = defender.getPartnerFight().entrySet().iterator();
			while (it.hasNext()) {
				Entry<Integer, Long> entry = it.next();
				if (entry.getValue() != 0) {
					Partner partner = defender.findPartner(entry.getValue());
					if (partner != null) {
						battleCharacter.addPartner(entry.getValue()); // 添加出战伙伴
						int site= BattleServer.partnerLeftSite(entry.getKey());
						battleInfo.addCharacterInToLeft(site, BattleServer.changeBattleCharacter(site, partner, EDirection.left)); // 伙伴
					}
				}
			}
//			Long partnerId = defender.getPartnerFight().get(1);
//			if (partnerId != null && partnerId != 0) {
//				Partner partner = defender.findPartner(partnerId);
//				if (partner != null) {
//					battleCharacter.addPartner(partnerId); // 添加出战伙伴
//					battleInfo.addCharacterInToLeft(4, FightServer.changeBattleCharacter(4, partner, EDirection.left)); // 伙伴
//				}
//			}
			battleInfo.setRightLeader(defender);
		} else {
			Team team = defender.getTeam();
			for (int i = 0; i < team.getMember().size(); i++) {
				Role member = team.getMember().get(i);
				BattleCharacter battleCharacter = FightServer.changeBattleCharacter(1 + i, member, EDirection.left);
				battleInfo.addCharacterInToLeft(1 + i, battleCharacter); // 玩家
				Long partnerId = member.getPartnerFight().get(1);
				if (partnerId != null && partnerId != 0) {
					Partner partner = member.findPartner(partnerId);
					if (partner != null) {
						battleCharacter.addPartner(partnerId); // 添加出战伙伴
						battleInfo.addCharacterInToLeft(4 + i, FightServer.changeBattleCharacter(4 + i, partner, EDirection.left)); // 伙伴
					}
				}

				if (i == 0) {
					battleInfo.setRightLeader(member);
				}
			}
		}

		// ======填充战斗信息======
		battleInfo.setBattleId(BattleManager.instance().getBattleId()); // 设置战斗编号
		if (battleInfo.isAuto()) {
			battleInfo.setWaitingTime(System.currentTimeMillis()); // 设置战斗时间
		} else {
			battleInfo.setWaitingTime(System.currentTimeMillis() + BattleManager.BATTLE_DURATION); // 设置战斗时间
		}
		battleInfo.setBaseMap(baseMap); // 设置战斗所在地图

		return battleInfo;
	}

	/**
	 * 战斗对象转换
	 * 
	 * @param index
	 *            队伍索引
	 * @param character
	 *            对象
	 * @param direction
	 *            方位
	 * @return 战斗对象
	 */
	public static BattleCharacter changeBattleCharacter(int index, ICharacter character, EDirection direction) {
		return new BattleCharacter(character, index, direction);
	}

	/**
	 * 抢矿战斗
	 * 
	 * @param robber
	 *            抢夺者
	 * @param owner
	 *            守矿者
	 * @return 抢矿成功
	 */
	public static boolean miningFight(Role robber, Role owner) {

		robber.setPrBattle(true);

		BattleInfo battleInfo = FightServer.preBattle(robber, owner, robber.getMapInfo().getBaseMap(), true);
		battleInfo.setBattleType(-1);
		robber.setBattle(true);
		robber.setBattleId(battleInfo.getBattleId()); // 设置战斗编号
		FightMessage.sendEncounterResult(battleInfo);
		robber.setPrBattle(false);
		while (!BattleServer.battle(battleInfo)) {
			FightMessage.sendFightResult(battleInfo);
		}
		FightMessage.sendFightResult(battleInfo);

		if (battleInfo.getResult().equals(EBattleReuslt.right)) {
			return true;
		} else {
			return false;
		}

	}
}
