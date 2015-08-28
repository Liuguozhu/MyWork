package iyunu.NewTLOL.timer;

import iyunu.NewTLOL.json.ActivityJson;
import iyunu.NewTLOL.json.HelperJson;
import iyunu.NewTLOL.json.RaidsJson;
import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.manager.ActivityPayManager;
import iyunu.NewTLOL.manager.BuyGoldManager;
import iyunu.NewTLOL.manager.JuHunManager;
import iyunu.NewTLOL.manager.OperationManager;
import iyunu.NewTLOL.manager.QiancengtaManager;
import iyunu.NewTLOL.manager.TaskManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.model.activity.instance.OnlineAwardInfo;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.model.gang.event.EGangActiveEvent;
import iyunu.NewTLOL.model.helper.instance.HelperAward;
import iyunu.NewTLOL.model.item.EEquip;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.seven.Seven;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.ETaskCategory;
import iyunu.NewTLOL.model.task.ETaskState;
import iyunu.NewTLOL.model.trials.Trials;
import iyunu.NewTLOL.model.vip.EVip;
import iyunu.NewTLOL.server.task.TaskServer;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class EveryDayCheck {

	public static void everydayChanges(Role role) {

		// 如果上次登录是昨天以前
		if (Time.beforeYesterday(role.getLogonTime())) {
			// 重置消耗
			role.setCostGold(0);
			// 清除副本次数
			role.getRaidsNumber().clear();
			// 聚魂
			JuHunManager.reset(role);
			// 刷新每日烧香
			role.setShaoXiangNum(0);
			// 每日登录加帮派活跃值
			if (role.getGangId() != 0) {
				Gang gang = GangManager.instance().getMap().get(role.getGangId());
				if (gang != null) {
					gang.addActive(EGangActiveEvent.每日登录.getActive(), EGangActiveEvent.每日登录.name(), role.getNick());
				}
			}

			// 招财
			role.setUseBuyGoldNum(BuyGoldManager.USE_BUY_GOLD_NUM);
			role.setFreeBuyGoldNum(BuyGoldManager.FREE_BUY_GOLD_NUM);
			role.setMoneyBuyGoldNum(BuyGoldManager.MONEY_BUY_GOLD_NUM);

			// 验证答题
			ActivityManager.resetDailyAnswer(role);

			// 每日刷新签到
			role.setHaveSign(1);
			// 连续登录设置奖，只在每日第一次登录时去添加奖
			// if (role.getLogonContinue() > 7) {
			// if ((role.getLogonContinue() - 1) % 7 == 0) {
			// role.getConPick().clear();
			// }
			// if (role.getLogonContinue() % 7 == 0) {
			// role.addConPick(7);
			// // role.setCon(7);
			// } else {
			// role.addConPick(role.getLogonContinue() % 7);
			// // role.setCon(role.getLogonContinue() % 7);
			// }
			// } else {
			// // role.setCon(role.getLogonContinue());
			// role.addConPick(role.getLogonContinue());
			// }

			// 试练
			for (Trials trials : role.getTrials().values()) {
				trials.clear();
			}

			// 活跃度礼包
			role.setLivenessScore(0);
			role.getLivenessScoreMap().clear();
			List<HelperAward> helperAwards = HelperJson.instance().getHelperAwards();

			for (HelperAward helperAward : helperAwards) {
				role.getLivenessScoreMap().put(helperAward.getScore(), 0);
			}

			// 重置在线礼包
			role.setOnlineTime(0);
			role.getOnlineAwardStateMap().clear();
			List<OnlineAwardInfo> onlineAwardInfoList = ActivityJson.instance().getActivityOnlineList();
			for (OnlineAwardInfo onlineAwardInfo : onlineAwardInfoList) {
				role.getOnlineAwardStateMap().put(onlineAwardInfo.getId(), 0);
			}

			// 重置门派任务总次数
			if (role.getGuildTask() != null) {
				TaskServer.setGuildtTaskNum(role, 1);
				TaskServer.setGuildtTaskSum(role, 1);
			} else {
				TaskServer.setGuildtTaskNum(role, 0);
				TaskServer.setGuildtTaskSum(role, 0);
			}

			// 重置江湖追杀令总次数
			TaskServer.setGhostTaskSum(role, 1);
			TaskServer.setGhostTaskNum(role, 1);

			Iterator<BaseTask> it = role.getTasks().values().iterator();
			while (it.hasNext()) {
				BaseTask baseTask = it.next();
				if (baseTask.getCategory().equals(ETaskCategory.ghost)) {
					it.remove();
					role.setGhostTask(null);
				}
			}

			// 重置寻宝次数
			// role.setHuntTreasureNum(10);

			role.getVip().check(); // 检查VIP状态
			role.getHelper().clear(); // 清除小助手

			role.setEliteMonsterNum(0);

			LogManager.roleFirstLogon(role); // 第一次登录日志
			// 清除VIP领取记录
			role.setVipGift(0);
			// 千层塔
			role.setNullResetNum(QiancengtaManager.NULL_RESET_NUMBER);
			role.setMoneyResetNum(QiancengtaManager.MONEY_RESET_NUMBER);

			// 试练重置的使用次数
			for (Integer degree : RaidsJson.instance().getTrialsListMap().keySet()) {
				role.getResetTrials().put(degree, 0);
			}

			// 英雄帖次数
			role.setYxtTaskNum(0);

			// 帮派入侵击杀怪物数量
			role.setKillMonsterNum(10);

			// 帮派活跃度每周领取状态
			role.setActivityType(0);

			// 发布任务次数
			role.setTaskFblNum(TaskManager.FBL_TASK_NUM);

			// 每日清空城主禁言
			if (GangManager.championLeader != 0 && role.getId() == GangManager.championLeader) {
				GangManager.muteIds.clear();
			}
			// ============七天开服礼包================
			role.getSevenMap().clear();
			int days = Time.getDaysBetween(OperationManager.OPEN_FU) + 1;
			if (days < 8 && days >= 1) {
				Map<Integer, Seven> sevenMap = role.getSevenMap();
				Seven sevenModel = new Seven();
				sevenModel.setCommon(1);
				if (role.getVip().getLevel() != EVip.common) {
					sevenModel.setVip(1);
				} else {
					sevenModel.setVip(0);
				}
				sevenMap.put(days, sevenModel);
			}
			// 每日累积充值清空

			if (ActivityPayManager.payEveryday.containsKey(role.getId())) {
				ActivityPayManager.payEveryday.remove(role.getId());
			}
			if (ActivityPayManager.payEverydayAwards.containsKey(role.getId())) {
				ActivityPayManager.payEverydayAwards.remove(role.getId());
			}
			role.setEmoCount(0);
		}

		// ================每日18点==================================================================
		long sixPM = Time.getTime(18, 0, 0);
		if (role.getLogonTime() < sixPM && System.currentTimeMillis() > sixPM) {
			// 清除副本次数
			role.getRaidsNumber().clear();
		}

		// ================以下不是每日新登录的检测==================================================================

		if (Time.lastWeek(role.getLogonTime())) {
			// 神兵的幸运值清零
			if (role.getEquipments().containsKey(EEquip.shenbing)) {
				role.getEquipments().get(EEquip.shenbing).setLuck(0);
			}

			for (Cell cell : role.getBag().getCells()) {
				if (cell.getItem() != null && cell.getItem() instanceof Equip) {
					Equip equip = (Equip) cell.getItem();
					equip.setLuck(0);
				}
			}

			role.setTaskCycleRow(0); // 重置环任务轮数
			if (role.getTaskCycle() != null) {
				role.getTaskCycle().setState(ETaskState.failed);
			}
		}

		// 每周六0点清除活跃度
		if (Time.lastDayOfWeek(role.getLogonTime(), 7)) { // 上次登录
			role.setGangActivity(0);
		}
		// 如果没有，说明开服当天， 玩家用旧代码登录过，所以没有初始化七天礼包，在此加上
		int days = Time.getDaysBetween(OperationManager.OPEN_FU) + 1;
		if (days < 8 && days >= 1) {
			if (!role.getSevenMap().containsKey(days)) {
				Map<Integer, Seven> sevenMap = role.getSevenMap();
				Seven sevenModel = new Seven();
				sevenModel.setCommon(1);
				if (role.getVip().getLevel() != EVip.common) {
					sevenModel.setVip(1);
				} else {
					sevenModel.setVip(0);
				}
				sevenMap.put(days, sevenModel);
			}
		}
		// 兼容老用户的帮贡
		if (role.getGangId() != 0 && role.getGang() != null) {
			if (role.getTotalTribute() == 0 && role.getTribute() != 0) {
				role.setTotalTribute(role.getTribute());
			}
		}

	}
}
