package iyunu.NewTLOL.net.protocol.task;

import iyunu.NewTLOL.enumeration.common.EExp;
import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.enumeration.partner.EGetPartner;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.PartnerJson;
import iyunu.NewTLOL.manager.PartnerManager;
import iyunu.NewTLOL.manager.TaskManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.message.TaskMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.helper.EHelper;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.EOpen;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.partner.EpartnerOperate;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.ETaskCategory;
import iyunu.NewTLOL.model.task.ETaskState;
import iyunu.NewTLOL.model.task.ETaskType;
import iyunu.NewTLOL.model.task.TaskAwardItem;
import iyunu.NewTLOL.model.task.TaskAwardPartner;
import iyunu.NewTLOL.model.task.instance.CollectionTask;
import iyunu.NewTLOL.model.task.instance.PickTask;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.award.AwardServer;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.server.base.ExpServer;
import iyunu.NewTLOL.server.base.GoldServer;
import iyunu.NewTLOL.server.helper.HelperServer;
import iyunu.NewTLOL.server.mail.MailServer;
import iyunu.NewTLOL.server.partner.PartnerServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.server.task.TaskServer;
import iyunu.NewTLOL.util.Util;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 完成任务
 * 
 * @author SunHonglei
 * 
 */
public class FinishTask extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";
	private int taskId;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "完成任务成功";

		taskId = msg.readInt("taskId");
		int type = msg.readInt("type");

		BaseTask baseTask = online.getTasks().get(taskId);
		if (baseTask == null) {
			result = 1;
			reason = "领取失败，任务不存在";
			return;
		}

		if (!baseTask.getState().equals(ETaskState.finish)) {
			result = 1;
			reason = "任务未完成";
			return;
		}

		if (type == 1 && !RoleServer.costCoin(online, 1500, EGold.taskCost)) {
			result = 1;
			reason = "";
			SendMessage.refreshNoCoin(online, 2);
			return;
		}

		if (baseTask.getCategory().equals(ETaskCategory.yingxiongtie)) {
			if (online.getYxtTaskNum() >= online.getVip().getLevel().getYingxiongtieAdd() + 5) {
				result = 1;
				reason = "今日英雄帖任务已到上限";
				return;
			}
		}

		if (baseTask.getCategory().equals(ETaskCategory.guild)) { // ==========================门派任务==========================

			Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

			if (baseTask.getType().equals(ETaskType.collectionTask)) {
				CollectionTask collectionTask = (CollectionTask) baseTask;
				Set<Entry<Integer, Integer>> set = collectionTask.getNeeds().entrySet();
				for (Iterator<Entry<Integer, Integer>> it = set.iterator(); it.hasNext();) {
					Entry<Integer, Integer> entry = it.next();
					boolean result = online.getBag().removeById(entry.getKey(), entry.getValue(), cellsMap, EItemCost.task);
					if (!result) {
						online.getBagStone().removeById(entry.getKey(), entry.getValue(), cellsMap, EItemCost.task);
					}
				}
			} else if (baseTask.getType().equals(ETaskType.pickTask)) {
				PickTask pickTask = (PickTask) baseTask;
				Set<Entry<Integer, Integer>> set = pickTask.getNeeds().entrySet();
				for (Iterator<Entry<Integer, Integer>> it = set.iterator(); it.hasNext();) {
					Entry<Integer, Integer> entry = it.next();
					online.getBag().cleanById(entry.getKey(), cellsMap);
				}
			}

			online.getTasks().remove(taskId);
			online.setGuildTask(null);

			int additionExp = ExpServer.additionBuff(online);
			if (type == 1) {
				additionExp += 2000;
			}
			int exp = (int) (baseTask.getExp() * ((10000 + additionExp) / 10000f));

			int additionGold = GoldServer.additionBuff(online);
			int gold = (int) (baseTask.getGold() * ((10000 + additionGold) / 10000f));

			// 增加经验
			RoleServer.addExp(online, exp, EExp.task);
			// 增加银两
			RoleServer.addGold(online, gold, EGold.task);

			if (online.getGuildTaskSum() == 5) { // 每日首轮最后一个任务给予物品奖励
				// 任务奖励
				if (baseTask.getReward().equals(EOpen.all)) {
					for (TaskAwardItem taskAwardItem : baseTask.getItemList()) {
						if (Util.probable(taskAwardItem.getProbability())) {
							Item item = ItemJson.instance().getItem(taskAwardItem.getItemId());
							item.setIsDeal(taskAwardItem.getIsBind());
							BagServer.add(online, item, taskAwardItem.getNum(), cellsMap, EItemGet.task);
						}
					}
				} else {
					for (TaskAwardItem taskAwardItem : baseTask.getItemList()) {
						if (Util.probable(taskAwardItem.getProbability())) {
							Item item = ItemJson.instance().getItem(taskAwardItem.getItemId());
							item.setIsDeal(taskAwardItem.getIsBind());
							BagServer.add(online, item, taskAwardItem.getNum(), cellsMap, EItemGet.task);
							break;
						}
					}
				}

				for (TaskAwardPartner taskAwardPartner : baseTask.getPartnerList()) {
					if (Util.probable(taskAwardPartner.getProbability())) {
						Partner newPartner = PartnerJson.instance().getNewPartner(taskAwardPartner.getIndex());
						newPartner = PartnerServer.addPartner(online, newPartner, EGetPartner.monster);
						newPartner.setIsBind(taskAwardPartner.getIsBind());

						// 背包已满或已达到携带伙伴上限,邮件下发
						if (online.getPartnerMap().size() >= PartnerManager.MAX_NUM) {
							MailServer.send(online.getId(), "系统邮件", "任务获得伙伴", null, 0, 0, 0, 0, newPartner);
						} else {
							List<Partner> partnerList = new ArrayList<>();
							newPartner.setOperateFlag(EpartnerOperate.add);
							partnerList.add(newPartner);
							PartnerMessage.sendPartners(online, partnerList);

							AwardServer.addPartner(online, newPartner);
						}
					}
				}

				// TODO 检查任务
				TaskServer.finishTaskByType(online, ETaskType.finishGuildTask);
			}

			if (!cellsMap.isEmpty()) {
				BagMessage.sendBag(online, cellsMap);
			}

			if (online.getGuildTaskNum() < TaskManager.GUILD_TASK_NUM) { // 自动接受同一轮内下一个任务
				TaskServer.getGuildTask(online);
			}

			HelperServer.helper(online, EHelper.guild); // 小助手记录
			LogManager.taskLog(online, taskId, 1); // 游戏日志
			return;
		} else { // ==============完成其他任务=====================
			TaskServer.finishTask(online, baseTask);
			// LogManager.taskLog(online, taskId, 1); // 游戏日志
		}

		if (online.getTeam() != null) {
			for (Role role : online.getTeam().getMember()) {
				if (role.getId() != online.getId()) {

					BaseTask memberTask = role.getTasks().get(taskId);
					if (memberTask == null) {
						continue;
					}

					if (!memberTask.getState().equals(ETaskState.finish)) {
						continue;
					}

					if (!baseTask.getCategory().equals(ETaskCategory.guild)) {
						TaskServer.finishTask(role, memberTask);
						TaskMessage.sendTask(role);
					}
				}
			}
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;

		try {
			llpMessage = LlpJava.instance().getMessage("s_finishTask");
			llpMessage.write("result", result);
			llpMessage.write("reason", reason);
			llpMessage.write("taskId", taskId);
			channel.write(llpMessage);
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}

		if (result == 0) {
			TaskMessage.sendTask(online);
		}
	}

}
