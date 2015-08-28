package iyunu.NewTLOL.message;

import iyunu.NewTLOL.json.BloodJson;
import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.manager.BloodManager;
import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.manager.gang.GangFightManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.battle.EBattleBuffType;
import iyunu.NewTLOL.model.battle.amin.BattleAmin;
import iyunu.NewTLOL.model.battle.amin.BattleAminInfo;
import iyunu.NewTLOL.model.battle.amin.BufferAminInfo;
import iyunu.NewTLOL.model.blood.ReBornRes;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.gang.event.EGangTributeEvent;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.team.Team;
import iyunu.NewTLOL.server.blood.BloodServer;
import iyunu.NewTLOL.server.gang.GangServer;
import iyunu.NewTLOL.server.map.MapServer;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class FightMessage {

	/**
	 * 封装遇怪信息
	 * 
	 * @param llpMessage
	 *            协议对象
	 * @param battleInfo
	 *            战斗对象
	 */
	public static void packedEncounterInfo(LlpMessage llpMessage, BattleInfo battleInfo) {
		llpMessage.write("battleId", battleInfo.getBattleId());
		llpMessage.write("turn", battleInfo.getTurn());
		for (BattleCharacter character : battleInfo.getAll().values()) {
			LlpMessage message = llpMessage.write("members");
			message.write("index", character.getIndex());
			message.write("id", character.getCharacter().getId());
			message.write("name", character.getCharacter().getNick());
			message.write("figure", character.getCharacter().getFigure());
			message.write("type", character.getCharacter().getType());
			message.write("hp", character.getCharacter().getHp());
			message.write("hpMax", character.getCharacter().getHpMax());
			message.write("mp", character.getCharacter().getMp());
			message.write("mpMax", character.getCharacter().getMpMax());
			message.write("side", character.getDirection().ordinal());
			message.write("shenbingId", character.getShenbingId());
			message.write("shizhuangId", character.getShizhuangId());
			message.write("worth", character.getCharacter().worth());
		}
		llpMessage.write("battleType", battleInfo.getBattleType());
	}

	/**
	 * 发送遇怪信息
	 * 
	 * @param battleInfo
	 *            战斗对象
	 * @param role
	 *            角色对象
	 */
	public static void sendEncounterResult(BattleInfo battleInfo, Role role) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_encounter_result");
			packedEncounterInfo(llpMessage, battleInfo);
			if (role != null && role.getChannel() != null) {
//				LogManager.logOut("第" + battleInfo.getTurn() + "轮  【" + role.getNick() + "】发送遇怪消息");
				role.getChannel().write(llpMessage);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 发送遇怪信息
	 * 
	 * @param battleInfo
	 *            战斗对象
	 */
	public static void sendEncounterResult(BattleInfo battleInfo) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_encounter_result");
			packedEncounterInfo(llpMessage, battleInfo);
			for (BattleCharacter character : battleInfo.getAll().values()) {
				if (character.getCharacter() instanceof Role) {
					Role role = (Role) character.getCharacter();
					if (role != null && role.getChannel() != null) {
//						LogManager.logOut("第" + battleInfo.getTurn() + "轮  【" + role.getNick() + "】发送遇怪消息");
						role.getChannel().write(llpMessage);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
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
	public static void sendFightResult(BattleInfo battleInfo, Role role) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_fight_result");
			int result = 2;

			llpMessage.write("result", result);
			llpMessage.write("battleId", battleInfo.getBattleId());
			llpMessage.write("turn", battleInfo.getTurn());

			// LogManager.battle("result=" + result);
			// LogManager.battle("battleId=" + battleInfo.getBattleId());
			// LogManager.battle("turn=" + battleInfo.getTurn());

			battleAmin(llpMessage, battleInfo);

			// LogManager.battle("第" + battleInfo.getTurn() + "轮  【" +
			// role.getNick() + "】发送战斗消息[result=" + result + "]");
			role.setBattle(false);
			role.setBattleId(0); // 清除战斗编号
//			SendMessage.sendHpAndMp(role); // 刷新生命值和内力值
//			if (role.getPartnerFight().get(1) != null && role.findPartner(role.getPartnerFight().get(1)) != null) {
//				PartnerMessage.sendPartnerHp(role, role.findPartner(role.getPartnerFight().get(1)));
//			}
			if (role != null && role.getChannel() != null) {
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	public static void sendFightRightResult(BattleInfo battleInfo, Map<Integer, BattleCharacter> roles) {
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
			// LogManager.battle("result=" + result);
			llpMessage.write("battleId", battleInfo.getBattleId());
			// LogManager.battle("battleId=" + battleInfo.getBattleId());
			llpMessage.write("turn", battleInfo.getTurn());
			// LogManager.battle("turn=" + battleInfo.getTurn());

			battleAmin(llpMessage, battleInfo);

			for (BattleCharacter battleCharacter : roles.values()) {

				if (result == 0 && battleCharacter.isEscape()) {
					continue;
				}

				// BattleServer.syncHpAndMp(battleCharacter); // 同步生命值和内力值

				if (battleCharacter.getCharacter() instanceof Role) {
					Role role = (Role) battleCharacter.getCharacter();

					if (result != 0) {
						role.setBattle(false);
						role.setBattleId(-1); // 清除战斗编号
					}

					if (role.getMapInfo().getMapId() == 16) {
						if (result == 2) {
							Team team = role.getTeam();
							if (team != null) {
								GangFightManager.quitGangFight(role, team);
							} else {
								GangFightManager.quitGangFight(role);
							}
						}

						if (result == 1) {
							Gang gang = GangManager.instance().getGang(role.getGangId());
							GangServer.countGangFight(role, gang, EGangTributeEvent.帮战);
						}
					}
					// 战斗结果的胜利或失败都放在这里~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					if (result == 1) {
						if (ActivityManager.BLOOD_STATE && (role.getBlood() == 1 || role.getBlood() == 2)) {
							// 取得对面的人数加 血战积分@@@此处是左边胜利了，取右边的人数
							BloodManager.instance().addElement(1, role, BloodManager.MARK);
							// 加红方或者蓝方的总分
							if (role.getBlood() == 2) {
								BloodServer.addLanMark(BloodManager.MARK);
							} else {
								BloodServer.addHongMark(BloodManager.MARK);
							}
						}
					}
					// 如果输了
					if (result == 2) {
						if (ActivityManager.BLOOD_STATE && (role.getBlood() == 1 || role.getBlood() == 2)) {
							// 被杀清除连杀
							BloodManager.instance().addElement(3, role, 1);
							if (role.getBlood() == 2) {
								BloodServer.addLanMark(1);
							} else {
								BloodServer.addHongMark(1);
							}
							// 被杀时间
							role.setBloodKillTime(System.currentTimeMillis());
							// 传送到出生点
							ReBornRes r = BloodJson.instance().getReBorn().get(role.getBlood());
							if (role.getMapInfo().getMapId() != r.getMap()) {
								if (role.getTeam() == null) {
									MapServer.changeMap(role, r.getX(), r.getY(), MapManager.instance().getMapById(r.getMap()), role.getMapInfo().getBaseMap());
								} else {
									MapServer.changeMap(role.getTeam(), r.getX(), r.getY(), MapManager.instance().getMapById(r.getMap()), role.getMapInfo().getBaseMap());
								}
							}
						}
					}

					if (role != null && role.getChannel() != null) {

//						if (!battleInfo.isAuto()) {
//							// 刷新生命值和内力值
//							SendMessage.sendHpAndMp(role);
//							if (role.getPartnerFight().get(1) != null && role.findPartner(role.getPartnerFight().get(1)) != null) {
//								PartnerMessage.sendPartnerHp(role, role.findPartner(role.getPartnerFight().get(1)));
//							}
//						}

						role.getChannel().write(llpMessage);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	public static void battleAmin(LlpMessage llpMessage, BattleInfo battleInfo) {

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
			LlpMessage attackerMessage = message.write("attacker");
			attackerMessage.write("index", battleAminInfo.getAttackerAmin().getIndex());
			// LogManager.battle("========攻击者=============index=" +
			// battleAminInfo.getAttackerAmin().getIndex());
			for (Integer hp : battleAminInfo.getAttackerAmin().getHps()) {
				attackerMessage.write("hp", hp);
				// LogManager.battle("========攻击者=========hp=" + hp);
			}

			attackerMessage.write("mp", battleAminInfo.getAttackerAmin().getMp());
			// LogManager.battle("========攻击者=========mp=" +
			// battleAminInfo.getAttackerAmin().getMp());
			attackerMessage.write("addBuff", battleAminInfo.getAttackerAmin().getBuff());
			// LogManager.battle("========攻击者=========addBuff=" +
			// battleAminInfo.getAttackerAmin().getBuff());
			attackerMessage.write("isDead", battleAminInfo.getAttackerAmin().getIsDead());
			// LogManager.battle("========攻击者====死亡=====isDead=" +
			// battleAminInfo.getAttackerAmin().getIsDead());
			attackerMessage.write("type", battleAminInfo.getAttackerAmin().getType());
			// LogManager.battle("========攻击者====类型=====type=" +
			// battleAminInfo.getAttackerAmin().getType());
			attackerMessage.write("skill", battleAminInfo.getAttackerAmin().getSkill());
			// LogManager.battle("========攻击者====技能=====skill=" +
			// battleAminInfo.getAttackerAmin().getSkill());

			List<BattleAmin> defenderAmins = battleAminInfo.getDefenderAmins();
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
				memberMessage.write("hp", character.getCharacter().getHp());
				memberMessage.write("hpMax", character.getCharacter().getHpMax());
				memberMessage.write("mp", character.getCharacter().getMp());
				memberMessage.write("mpMax", character.getCharacter().getMpMax());
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
				// LogManager.battle("===BUFF状态=======index=" + entry.getKey());
				for (Integer buffId : buffIds) {
					message.write("buffs", buffId);
					// character.removeBuff(buffId); // 删除BUFF
					// LogManager.battle("===BUFF状态===buffs=" + buffId);
				}
				// System.out.println();
			}
			// character.getDisappearBuffs().clear();
		}
		// LogManager.battle("BUFF状态 ------ 结束");
	}

	public static void sendFightLeftResult(BattleInfo battleInfo, Map<Integer, BattleCharacter> roles) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_fight_result");

			battleAmin(llpMessage, battleInfo);

			int result = 0;
			switch (battleInfo.getResult()) {
			case left: // 胜利
				result = 1;
				break;
			case right: // 失败
				result = 2;
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

			for (BattleCharacter battleCharacter : roles.values()) {

				if (result == 0 && battleCharacter.isEscape()) {
					continue;
				}

				// BattleServer.syncHpAndMp(battleCharacter); // 同步生命值和内力值

				if (battleCharacter.getCharacter() instanceof Role) {
					Role role = (Role) battleCharacter.getCharacter();

					if (result != 0) {
						role.setBattle(false);
						role.setBattleId(-1); // 清除战斗编号
					}

					if (role.getMapInfo().getMapId() == 16) {
						if (result == 2) {
							Team team = role.getTeam();
							if (team != null) {
								GangFightManager.quitGangFight(role, team);
							} else {
								GangFightManager.quitGangFight(role);
							}
						}

						if (result == 1) {
							Gang gang = GangManager.instance().getGang(role.getGangId());
							GangServer.countGangFight(role, gang, EGangTributeEvent.帮战);
						}
					}
					// 战斗结果的胜利或失败都放在这里~~~~~~~~~~~~~~~~~~~~~~~~~~~~
					if (result == 1) {
						if (ActivityManager.BLOOD_STATE && (role.getBlood() == 1 || role.getBlood() == 2)) {

							// 取得对面的人数加 血战积分、、此处是右边胜利了，取左边的人数
							BloodManager.instance().addElement(1, role, BloodManager.MARK);

							// 加红方或者蓝方的总分
							if (role.getBlood() == 1) {
								BloodServer.addHongMark(BloodManager.MARK);
							} else {
								BloodServer.addLanMark(BloodManager.MARK);
							}
						}

					}
					if (result == 2) {
						if (ActivityManager.BLOOD_STATE && (role.getBlood() == 1 || role.getBlood() == 2)) {
							// 被杀清除连杀
							BloodManager.instance().addElement(3, role, 1);
							// 加红方或者蓝方的总分
							if (role.getBlood() == 1) {
								BloodServer.addHongMark(1);
							} else {
								BloodServer.addLanMark(1);
							}
							// 被杀时间
							role.setBloodKillTime(System.currentTimeMillis());
							// 传送到出生点
							ReBornRes r = BloodJson.instance().getReBorn().get(role.getBlood());
							if (role.getMapInfo().getMapId() != r.getMap()) {
								if (role.getTeam() == null) {
									MapServer.changeMap(role, r.getX(), r.getY(), MapManager.instance().getMapById(r.getMap()), role.getMapInfo().getBaseMap());
								} else {
									MapServer.changeMap(role.getTeam(), r.getX(), r.getY(), MapManager.instance().getMapById(r.getMap()), role.getMapInfo().getBaseMap());
								}
							}
						}
					}

					if (role != null && role.getChannel() != null) {

//						if (!battleInfo.isAuto()) {
//							// 刷新生命值和内力值
//							SendMessage.sendHpAndMp(role);
//							if (role.getPartnerFight().get(1) != null && role.findPartner(role.getPartnerFight().get(1)) != null) {
//								PartnerMessage.sendPartnerHp(role, role.findPartner(role.getPartnerFight().get(1)));
//							}
//						}

						role.getChannel().write(llpMessage);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
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
	public static void sendFightResult(BattleInfo battleInfo) {

		switch (battleInfo.getResult()) {
		case left:
		case right:
			// BattleManager.instance().removeBattleProcessorCenter(battleInfo.getBattleId());
			// // 删除战斗信息
			break;
		default:
			break;
		}
		sendFightRightResult(battleInfo, battleInfo.getRight());
		if (battleInfo.getType() == 4) {
			sendFightLeftResult(battleInfo, battleInfo.getLeft());
		}

		for (BattleCharacter character : battleInfo.getAll().values()) {
			List<Integer> buffIds = character.getDisappearBuffs();
			if (buffIds != null && !buffIds.isEmpty()) {
				for (Integer buffId : buffIds) {
					character.removeBuff(buffId); // 删除BUFF
				}
			}
			character.getDisappearBuffs().clear();
		}
	}

	/**
	 * @function 帮派战战斗开始通知
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @param state
	 *            通知状态
	 * @date 2014年5月23日
	 */
	public static void payInform(Role role, int state) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_payInform");
				llpMessage.write("state", state);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：帮派战战斗开始通知");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}
