package iyunu.NewTLOL.net.protocol.battle;

import iyunu.NewTLOL.json.BloodJson;
import iyunu.NewTLOL.json.MonsterJson;
import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.manager.BattleManager;
import iyunu.NewTLOL.manager.EmoMapManager;
import iyunu.NewTLOL.message.FightMessage;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.buffRole.EBuffType;
import iyunu.NewTLOL.model.buffRole.instance.BuffRole;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.map.DistributionHidden;
import iyunu.NewTLOL.model.map.EMonsterOnMap;
import iyunu.NewTLOL.model.map.MonsterOnMap;
import iyunu.NewTLOL.model.map.instance.MapCommonInfo;
import iyunu.NewTLOL.model.monster.instance.MonsterGroup;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.instance.NPCFightTask;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.processor.BattleProcessorCenter;
import iyunu.NewTLOL.server.battle.BattleServer;
import iyunu.NewTLOL.server.buff.BuffServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.server.task.TaskServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 遇怪
 * 
 * @author SunHonglei
 * 
 */
public class Encounter extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private BattleInfo battleInfo;

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======重置数据======
		result = 0;
		reason = "遇怪成功";

		if (online.isBattle()) {
			result = 1;
			reason = "已战斗";
			return;
		}

		if (online.isPrBattle()) {
			result = 1;
			reason = "已申请战斗";
			return;
		}

		online.setPrBattle(true);
		// ======获取参数======
		int type = msg.readInt("type");

		if (type == 1) { // ==============================================NPC战斗==============================================
			int taskId = (int) msg.readLong("goal");
			if (TaskServer.chechTaskGot(online, taskId)) {
				BaseTask task = online.getTasks().get(taskId);
				if (task instanceof NPCFightTask) {
					NPCFightTask npcFightTask = (NPCFightTask) task;

					MonsterGroup monsterGroup = MonsterJson.instance().getMonsterGroup(npcFightTask.getNpcId());
					if (monsterGroup == null) {
						result = 1;
						reason = "怪物不存在！";
						return;
					}

					// =================特殊任务处理====================
					// if (monsterGroup.getId() == 10530) {
					// battleType = 1;
					// } else if (monsterGroup.getId() == 10475) {
					// battleType = 2;
					// }
					// =================特殊任务处理====================

					battleInfo = BattleServer.preBattle(online, monsterGroup, type, online.getMapInfo().getBaseMap(), false);

					if (battleInfo != null) {
						battleInfo.setTaskId(taskId); // 设置任务编号
					}
					return;
				}
			}

			result = 1;
			reason = "任务不存在";
			return;

		} else if (type == 2) { // ==============================================指定唯一编号战斗==============================================
			long uid = msg.readLong("goal");
			BaseMap baseMap = online.getMapInfo().getBaseMap();

			MonsterOnMap monsterOnMap = baseMap.getMonsterOnMap(uid);

			if (monsterOnMap == null) {
				MonsterOnMap ownerMonsterOnMap = online.getMapInfo().getMonsterOnMaps().get(uid);
				if (ownerMonsterOnMap == null) {
					result = 1;
					reason = "怪物不存在！";
					baseMap.removeMonster(uid);
					return;
				} else {
					monsterOnMap = ownerMonsterOnMap;
				}
			}

			if (online.getLevel() < monsterOnMap.getLevelLimit()) {
				result = 2;
				reason = "您的等级太低了，" + monsterOnMap.getLevelLimit() + "需要级才能挑战";
				return;
			}

			if (!monsterOnMap.canFight(online.getId())) {
				result = 2;
				reason = "你是谁，我不认识你！";
				return;
			}

			// 如果点的是守护怪
			if (ActivityManager.BLOOD_STATE) {
				if (BloodJson.instance().getBloodMonster().containsKey(monsterOnMap.getMonsterGroupId())) {
					if (online.getBlood() == 0) {
						result = 2;
						reason = "非血战成员不可攻击守护怪！";
						return;
					}
					if (online.getBlood() == 1 || online.getBlood() == 2) {
						if (online.getBlood() == BloodJson.instance().getBloodMonster().get(monsterOnMap.getMonsterGroupId()).getFlag()) {
							result = 2;
							reason = "不可攻击己方的守护怪！";
							return;
						}
					}
				}
			}

			if (monsterOnMap.isFighting()) {
				result = 2;
				reason = "少侠，您动作慢了一步，已经被别人抢先了！";
				return;
			}

			monsterOnMap.setFighting(true);

			MonsterGroup monsterGroup = MonsterJson.instance().getMonsterGroup(monsterOnMap.getMonsterGroupId());

			battleInfo = BattleServer.preBattle(online, monsterGroup, type, baseMap, false);
			battleInfo.setMonsterOnMap(monsterOnMap);
			if (monsterGroup.getTaskMark() == 1) {
				battleInfo.setAwardType(1);
			}

			switch (monsterOnMap.getType()) {
			case qidaeren:
			case drawing:
			case invasion:
			case xuezhan:
			case emo:
				if (monsterOnMap.getType() == EMonsterOnMap.emo) {
					// 添加到恶魔岛已被消灭的表里
					EmoMapManager.instance().getHasKillMonsterIds().add(monsterOnMap.copy());
				}
				// 删除怪物
				baseMap.removeMonsterOnMap(uid);
				break;
			default:
				break;
			}

		} else { // ==============================================野外遇怪==============================================

			if (online.getTeam() != null && online.getTeam().getLeader().getId() != online.getId()) {
				result = 1;
				reason = "您不是队长，不能主动遇怪！";
				return;
			}

			BuffRole buffRole = online.getBuffs().get(EBuffType.xunyicao);
			if (buffRole != null) {
				if (buffRole.getFinishTime() > System.currentTimeMillis()) {
					result = 1;
					reason = "时间未到，不能进入战斗";
					return;
				} else {
					BuffServer.removeBuff(online, EBuffType.xunyicao);
				}
			}

			// ======填充左边队伍（怪物）信息======
			BaseMap baseMap = online.getMapInfo().getBaseMap();
			if (baseMap != null && baseMap instanceof MapCommonInfo) {
				MapCommonInfo mapInfo = (MapCommonInfo) baseMap;
				if (mapInfo.getPve() == 0) {

					RoleServer.addEliteMonster(online); // 记录遇怪次数
					if (RoleServer.isEliteMonster(online)) { // 判断是否遇到精英怪

						for (DistributionHidden distribution : mapInfo.getEliteDistributions()) {
							if (distribution.isInHere(online.getMapInfo().getX(), online.getMapInfo().getY())) {

								MonsterGroup monsterGroup = MonsterJson.instance().getMonsterGroup(distribution.getMonsterGroup());
								if (monsterGroup == null) {
									continue;
								}
								battleInfo = BattleServer.preBattle(online, monsterGroup, type, baseMap, false);
								return;
							}
						}

					} else {

						for (DistributionHidden distribution : mapInfo.getDistributions()) {
							if (distribution.isInHere(online.getMapInfo().getX(), online.getMapInfo().getY())) {

								MonsterGroup monsterGroup = MonsterJson.instance().getMonsterGroup(distribution.getMonsterGroup());
								if (monsterGroup == null) {
									continue;
								}
								battleInfo = BattleServer.preBattle(online, monsterGroup, type, baseMap, false);
								return;
							}
						}
					}

					result = 1;
					reason = "此位置没有怪物";
					return;
				} else {
					result = 1;
					reason = "此位置不能进入战斗";
					return;
				}
			} else {
				result = 1;
				reason = "地图不能战斗";
				return;
			}
		}

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_encounter");
			message.write("result", result);
			message.write("reason", reason);

			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

		if (result == 0) {

			BattleManager.instance().addBattleProcessor(new BattleProcessorCenter(battleInfo));

			if (online.getTeam() == null) { // 单人遇怪
				online.setBattle(true);
				online.setBattleId(battleInfo.getBattleId()); // 设置战斗编号
				FightMessage.sendEncounterResult(battleInfo, online);
			} else { // 组队遇怪
				for (Role role : online.getTeam().getMember()) {
					role.setBattle(true);
					role.setBattleId(battleInfo.getBattleId()); // 设置战斗编号
				}
				FightMessage.sendEncounterResult(battleInfo);
			}
		}
		online.setPrBattle(false);
	}

}
