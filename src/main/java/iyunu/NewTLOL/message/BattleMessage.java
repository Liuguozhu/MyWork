package iyunu.NewTLOL.message;

import iyunu.NewTLOL.event.rank.RankEvent;
import iyunu.NewTLOL.json.BloodJson;
import iyunu.NewTLOL.json.RaidsJson;
import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.manager.BloodManager;
import iyunu.NewTLOL.manager.DailyManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.battle.EBattleBuffType;
import iyunu.NewTLOL.model.battle.amin.BattleAmin;
import iyunu.NewTLOL.model.battle.amin.BattleAminInfo;
import iyunu.NewTLOL.model.battle.amin.BufferAminInfo;
import iyunu.NewTLOL.model.map.instance.MapRaidsInfo;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.ETaskState;
import iyunu.NewTLOL.model.task.ETaskType;
import iyunu.NewTLOL.model.task.instance.GhostTask;
import iyunu.NewTLOL.model.task.instance.NPCFightTask;
import iyunu.NewTLOL.model.trials.Trials;
import iyunu.NewTLOL.model.trials.instance.TrialsInfo;
import iyunu.NewTLOL.server.battle.BattleServer;
import iyunu.NewTLOL.server.blood.BloodServer;
import iyunu.NewTLOL.server.task.TaskServer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class BattleMessage implements ApplicationEventPublisherAware {

	private static ApplicationEventPublisher publisher;

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}

	/**
	 * 发送战斗结果信息
	 * 
	 * @param role
	 *            角色对象
	 * @param battleId
	 *            战斗编号
	 * @throws Exception
	 *             异常信息
	 */
	public static void sendFightResult(BattleInfo battleInfo, Map<Integer, BattleCharacter> roles) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_fight_result");
			int result = 0;
			switch (battleInfo.getResult()) {
			case left: // 失败
				result = 2;
				break;
			case right: // 胜利
				result = 1;
				break;
			default: // 战斗未结束
				result = 0;
			}

			llpMessage.write("result", result);
			llpMessage.write("battleId", battleInfo.getBattleId());
			llpMessage.write("turn", battleInfo.getTurn());

			// LogManager.battle("result=" + result);
			// LogManager.battle("battleId=" + battleInfo.getBattleId());
			// LogManager.battle("turn=" + battleInfo.getTurn());

			// LogManager.battle("BUFF动画 ------ 开始");
			// ======BUFF动画======
			Set<Entry<EBattleBuffType, ArrayList<BufferAminInfo>>> set = battleInfo.getBuffInfos().entrySet();
			for (Iterator<Entry<EBattleBuffType, ArrayList<BufferAminInfo>>> it = set.iterator(); it.hasNext();) {
				Entry<EBattleBuffType, ArrayList<BufferAminInfo>> entry = it.next();

				ArrayList<BufferAminInfo> list = entry.getValue();
				if (!list.isEmpty()) {
					// LogManager.battle("======BUFF动画======");
					LlpMessage message = llpMessage.write("buffInfoList");
					for (BufferAminInfo bufferAminInfo : list) {
						LlpMessage msg = message.write("buffAminInfoList");
						msg.write("index", bufferAminInfo.getIndex());
						msg.write("hp", bufferAminInfo.getHp());
						msg.write("isDead", bufferAminInfo.getIsDead());
						// LogManager.battle("======BUFF动画==========index=" +
						// bufferAminInfo.getIndex());
						// LogManager.battle("======BUFF动画======hp=" +
						// bufferAminInfo.getHp());
						// LogManager.battle("======BUFF动画======isDead=" +
						// bufferAminInfo.getIsDead());
					}
				}
				// System.out.println();
			}
			// LogManager.battle("BUFF动画 ------ 结束");

			// LogManager.battle("战斗动画 ------ 开始");
			// ======战斗动画======
			List<BattleAminInfo> battleAminInfos = battleInfo.getBattleAminInfos();
			for (BattleAminInfo battleAminInfo : battleAminInfos) {
				LlpMessage message = llpMessage.write("battleAminInfoList");
				// ============================攻击者=============================
				LlpMessage attackerMessage = message.write("attacker");
				attackerMessage.write("index", battleAminInfo.getAttackerAmin().getIndex());
				// LogManager.battle("========攻击者=============index=" +
				// battleAminInfo.getAttackerAmin().getIndex());
				for (Integer hp : battleAminInfo.getAttackerAmin().getHps()) {
					attackerMessage.write("hp", hp);
					// LogManager.battle("========攻击者=========hp=" + hp);
				}

				attackerMessage.write("mp", battleAminInfo.getAttackerAmin().getMp());
				attackerMessage.write("addBuff", battleAminInfo.getAttackerAmin().getBuff());
				attackerMessage.write("isDead", battleAminInfo.getAttackerAmin().getIsDead());
				attackerMessage.write("type", battleAminInfo.getAttackerAmin().getType());
				attackerMessage.write("skill", battleAminInfo.getAttackerAmin().getSkill());
				// LogManager.battle("========攻击者=========mp=" +
				// battleAminInfo.getAttackerAmin().getMp());
				// LogManager.battle("========攻击者=========addBuff=" +
				// battleAminInfo.getAttackerAmin().getBuff());
				// LogManager.battle("========攻击者====死亡=====isDead=" +
				// battleAminInfo.getAttackerAmin().getIsDead());
				// LogManager.battle("========攻击者====类型=====type=" +
				// battleAminInfo.getAttackerAmin().getType());
				// LogManager.battle("========攻击者====技能=====skill=" +
				// battleAminInfo.getAttackerAmin().getSkill());

				List<BattleAmin> defenderAmins = battleAminInfo.getDefenderAmins();
				// ============================攻击者=============================
				for (BattleAmin defenderAmin : defenderAmins) {
					LlpMessage defenderMessage = message.write("defenders");
					defenderMessage.write("index", defenderAmin.getIndex());
					// LogManager.battle("========被攻击者=========index=" +
					// defenderAmin.getIndex());
					if (defenderAmin.getHps().size() > 0) {
						defenderMessage.write("hp", defenderAmin.getHps().get(0));
						// LogManager.battle("========被攻击者=========hp=" +
						// defenderAmin.getHps().get(0));
					} else {
						defenderMessage.write("hp", 0);
						// LogManager.battle("========被攻击者=========hp=0");
					}
					defenderMessage.write("mp", defenderAmin.getMp());
					// LogManager.battle("========被攻击者=========mp=" +
					// defenderAmin.getMp());
					for (Integer buffId : defenderAmin.getAddBuffs()) {
						defenderMessage.write("addBuffs", buffId);
						// LogManager.battle("增加BUFF=" + buffId);
					}
					for (Integer buffId : defenderAmin.getSubBuffs()) {
						defenderMessage.write("subBuffs", buffId);
						// LogManager.battle("减少BUFF=" + buffId);
					}
					defenderMessage.write("crit", defenderAmin.getCrit());
					// LogManager.battle("========被攻击者=====暴击====crit=" +
					// defenderAmin.getCrit());
					defenderMessage.write("parry", defenderAmin.getParry());
					// LogManager.battle("========被攻击者=====格挡====parry=" +
					// defenderAmin.getParry());
					defenderMessage.write("miss", defenderAmin.getMiss());
					// LogManager.battle("========被攻击者=====闪避====miss=" +
					// defenderAmin.getMiss());
					defenderMessage.write("isDead", defenderAmin.getIsDead());
					// LogManager.battle("========被攻击者=====死亡====isDead=" +
					// defenderAmin.getIsDead());
					defenderMessage.write("isDefense", defenderAmin.getIsDefense());
					// LogManager.battle("========被攻击者=====防御====isDefense=" +
					// defenderAmin.getIsDefense());
					defenderMessage.write("skill", (int) defenderAmin.getSkill());
					// LogManager.battle("========被攻击者=====技能====skill=" +
					// defenderAmin.getSkill());
				}

				if (battleAminInfo.getMember() != null) {
					// LogManager.logOut("======召唤伙伴======");
					LlpMessage memberMessage = message.write("member");
					BattleCharacter character = battleAminInfo.getMember();
					memberMessage.write("index", character.getIndex());
					// LogManager.logOut("======召唤伙伴======index===" +
					// character.getIndex());
					memberMessage.write("id", character.getCharacter().getId());
					// LogManager.logOut("======召唤伙伴======id===" +
					// character.getCharacter().getId());
					memberMessage.write("name", character.getCharacter().getNick());
					// LogManager.logOut("======召唤伙伴======name===" +
					// character.getCharacter().getNick());
					memberMessage.write("figure", character.getCharacter().getFigure());
					// LogManager.logOut("======召唤伙伴======figure===" +
					// character.getCharacter().getFigure());
					memberMessage.write("type", character.getCharacter().getType());

					memberMessage.write("hp", character.getPropertyBattleLog().getHp());
					// LogManager.logOut("======召唤伙伴======hp===" +
					// character.getPropertyBattleLog().getHp());
					memberMessage.write("hpMax", character.getPropertyBattleLog().getHpMax());
					// LogManager.logOut("======召唤伙伴======hpMax===" +
					// character.getPropertyBattleLog().getHpMax());
					memberMessage.write("side", character.getDirection().ordinal());
				}
				// System.out.println();
				// System.out.println();
			}
			// LogManager.battle("战斗动画 ------ 结束");

			// LogManager.battle("BUFF状态 ------ 开始");
			// ====== buff状态集合 ======
			Map<Integer, BattleCharacter> all = battleInfo.getAll();
			for (Iterator<Entry<Integer, BattleCharacter>> it = all.entrySet().iterator(); it.hasNext();) {
				Entry<Integer, BattleCharacter> entry = it.next();
				BattleCharacter character = entry.getValue();

				List<Integer> buffIds = character.getDisappearBuffs();
				if (buffIds != null && !buffIds.isEmpty()) {
					LlpMessage message = llpMessage.write("buffStateList");
					message.write("index", entry.getKey());
					// LogManager.battle("===BUFF状态=======index=" +
					// entry.getKey());

					for (Integer buffId : buffIds) {
						message.write("buffs", buffId);
						character.removeBuff(buffId); // 删除BUFF
						// LogManager.battle("===BUFF状态===buffs=" + buffId);
					}
					// System.out.println();
				}
				character.getDisappearBuffs().clear();
			}
			// LogManager.battle("BUFF状态 ------ 结束");

			if (result != 0) {
				// BattleManager.instance().removeBattleProcessorCenter(battleInfo.getBattleId());
				// // 删除战斗信息
			}

			Iterator<Entry<Integer, BattleCharacter>> it = roles.entrySet().iterator();
			while (it.hasNext()) {
				Entry<Integer, BattleCharacter> entry = it.next();

				if (result == 0 && entry.getValue().isEscape()) {
					it.remove();
					continue;
				}

				if (entry.getValue().getCharacter() instanceof Role) {
					Role role = (Role) entry.getValue().getCharacter();
					if (role != null && role.getChannel() != null) {
						role.getChannel().write(llpMessage);
						// LogManager.battle("第" + battleInfo.getTurn() + "轮  【"
						// + role.getNick() + "】发送战斗消息[result=" + result + "]");

						if (result != 0) {
							role.setBattle(false);
							role.setBattleId(-1); // 清除战斗编号
						}

						if (result == 1) { // ======战斗奖励（只有战斗胜利或失败时下发）======
							if (battleInfo.getType() == 1) { // ======NPC战斗======
								// ======检查是否有任务======
								if (TaskServer.chechTaskGot(role, battleInfo.getTaskId())) {
									BaseTask task = role.getTasks().get(battleInfo.getTaskId());
									if (task instanceof NPCFightTask) {
										NPCFightTask npcFightTask = (NPCFightTask) task;
										npcFightTask.finish();
									}
								}
							} else if (battleInfo.getType() == 2) { // 指定唯一编号杀怪

								role.getMapInfo().removeMonsterOnMap(battleInfo.getMonsterOnMap()); // 删除个人格子中怪物

								if (role.getGhostTask() != null) { // 江湖追杀令
									GhostTask ghostTask = (GhostTask) role.getGhostTask();
									if (ghostTask.getUid() == battleInfo.getMonsterOnMap().getUid()) {
										role.getGhostTask().setState(ETaskState.finish);
										TaskMessage.sendTask(role);
									}
								}
								// 如果被杀死的怪是血战怪，那么
								if (BloodJson.instance().getBloodMonster().containsKey(battleInfo.getMonsterOnMap().getMonsterGroupId())) {
									// 处于血战时间，并玩家身上有血点标记
									if (ActivityManager.BLOOD_STATE && (role.getBlood() == 1 || role.getBlood() == 2)) {

										// 取得怪物的分
										int mark = BloodJson.instance().getBloodMonster().get(battleInfo.getMonsterOnMap().getMonsterGroupId()).getMark();
										// 个人加分
										BloodManager.instance().addElement(2, role, mark);

										// 加红方或者蓝方的总分
										if (role.getBlood() == 2) {
											BloodServer.addLanMark(mark);
										} else {
											BloodServer.addHongMark(mark);
										}
									}
								}

							} else if (battleInfo.getType() == 3) { // 试炼
								Trials trials = role.getTrials().get(battleInfo.getTrialsId());
								if (trials.increasePosition()) {

									boolean isNext = true;
									for (TrialsInfo trialsInfo : RaidsJson.instance().getTrialsInfoList(battleInfo.getTrialsId())) {
										if (role.getTrials().get(trialsInfo.getTrialsId()).getState() == 0) {
											isNext = false;
											break;
										}
									}

									if (isNext) {
										role.setTrialsState(role.getTrialsState() + 1);
									}
									TrialsMessage.refreshTrialsState(role);
								}

								TrialsMessage.sendKillPosition(role, battleInfo.getTrialsId());

							} else if (battleInfo.getType() == 5) {
								// ------------------每日任务---------------------
								try {
									List<Integer> dailyFudi = DailyManager.instance().getDailyFuDiList();
									if (dailyFudi != null && dailyFudi.size() > 0) {
										for (Integer dailyId : dailyFudi) {
											if (DailyManager.instance().checkEvent(dailyId)) {
												DailyManager.instance().finishDailyFudi(role.getDailyMap().get(dailyId), role, role.getCurrentFloorId());
											}
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								// ------------------每日任务---------------------
								role.setCurrentFloorId(battleInfo.getCurrentFloorId() + 1);

								if (battleInfo.getCurrentFloorId() > role.getHistoryFloorId()) {
									role.setHistoryFloorId(battleInfo.getCurrentFloorId());
									RankEvent.QctEvent.handleEvent(role.toCard()); // 塔排行榜
								}

								SendMessage.sendCurrentFloorId(role);
							}
							BattleServer.award(role, battleInfo); // ===========================================战斗奖励===========================================
						}
						// 刷新生命值和内力值
//						SendMessage.sendHpAndMp(role);
//						if (role.getPartnerFight().get(1) != null && role.findPartner(role.getPartnerFight().get(1)) != null) {
//							PartnerMessage.sendPartnerHp(role, role.findPartner(role.getPartnerFight().get(1)));
//						}
					}
				}
			}

			// ======杀地图上明怪======
			if (battleInfo.getType() == 2) {

				if (result == 1) {

					if (battleInfo.getBaseMap().getMonsters().containsKey(battleInfo.getMonsterOnMap().getUid())) {
						battleInfo.getBaseMap().removeMonster(battleInfo.getMonsterOnMap().getUid()); // 删除格子中怪物
					}

					if (battleInfo.getBaseMap() instanceof MapRaidsInfo) { // 如果是在副本地图中
						MapRaidsInfo mapRaidsInfo = (MapRaidsInfo) battleInfo.getBaseMap();
						mapRaidsInfo.setState(1); // 记录当前格子——通关

						if (mapRaidsInfo.isBoss()) {
							mapRaidsInfo.getRaidsTeamInfo().getRaidsFloor().setState(1); // 如果是BOSS记录本层通关
						}

						if (mapRaidsInfo.isLastBoss()) { // 整个副本通关，发奖励
							mapRaidsInfo.getRaidsTeamInfo().setRaidsState(1);

							// 刷宝箱
							mapRaidsInfo.addItem(28, 18, mapRaidsInfo.getRaidsTeamInfo().getRaidsInfo().getBox());
							for (Long roleId : mapRaidsInfo.getRaidsTeamInfo().getMemberIds()) {
								if (ServerManager.instance().isOnline(roleId)) {
									Role role = ServerManager.instance().getOnlinePlayer(roleId);
									// ======检查是否有任务（加入门派任务）======
									TaskServer.finishTaskByType(role, ETaskType.finishRaidsTask);
								}
							}
						}

						RaidsMessage.refreshRaids(mapRaidsInfo); // 刷新副本状态
						RaidsMessage.refreshMiniMap(mapRaidsInfo.getRaidsTeamInfo());
					}

				} else {
					if (result == 2) {
						battleInfo.getMonsterOnMap().setFighting(false); // 标记为未战斗
					}
				}

			}
			// ======杀地图上明怪======
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}
