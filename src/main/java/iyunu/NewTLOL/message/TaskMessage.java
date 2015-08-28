package iyunu.NewTLOL.message;

import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.ETaskCategory;
import iyunu.NewTLOL.model.task.ETaskState;
import iyunu.NewTLOL.model.task.ETaskType;
import iyunu.NewTLOL.model.task.TaskAwardItem;
import iyunu.NewTLOL.model.task.TaskAwardPartner;
import iyunu.NewTLOL.model.task.instance.CollectionTask;
import iyunu.NewTLOL.model.task.instance.KillTask;
import iyunu.NewTLOL.model.task.instance.PickTask;
import iyunu.NewTLOL.model.task.instance.YingxiongtieTask;
import iyunu.NewTLOL.util.Translate;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class TaskMessage {

	/**
	 * 刷新环任务数量
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshTaskCycleNum(Role role) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_refreshCycleTaskNum");
			llpMessage.write("num", role.getTaskCycleNum());
			if (role != null && role.getChannel() != null && role.isLogon()) {
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新环任务数量");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}

	}

	/**
	 * 刷新江湖追杀令任务数量
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshGhostTaskNum(Role role) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_refreshGhostTaskNum");
			llpMessage.write("num", role.getGhostTaskNum());
			if (role != null && role.getChannel() != null && role.isLogon()) {
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新江湖追杀令任务数量");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}

	}

	/**
	 * 刷新门派任务数量
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshGuildTaskNum(Role role) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_refreshGuildTaskNum");
			llpMessage.write("num", role.getGuildTaskNum());
			if (role != null && role.getChannel() != null && role.isLogon()) {
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送门派任务数量");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}

	}

	/**
	 * 发送任务信息
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendTask(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshTask");
				packageTaskMsg(role.getTasks(), llpMessage, role);
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送任务信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 封装任务协议
	 * 
	 * @param tasks
	 *            任务集合
	 * @param llpMessage
	 *            协议对象
	 */
	public static void packageTaskMsg(Map<Integer, BaseTask> tasks, LlpMessage llpMessage, Role role) {
		Set<Entry<Integer, BaseTask>> taskSet = tasks.entrySet();
		for (Iterator<Entry<Integer, BaseTask>> taskIt = taskSet.iterator(); taskIt.hasNext();) {
			BaseTask baseTask = taskIt.next().getValue();

			if (!baseTask.getState().equals(ETaskState.none)) {
				LlpMessage message = llpMessage.write("taskInfoList");
				message.write("taskId", baseTask.getId());
				message.write("state", baseTask.getState().ordinal());
				message.write("category", baseTask.getCategory().ordinal());
				message.write("exp", baseTask.getExp());
				message.write("gold", baseTask.getGold());
				message.write("coin", baseTask.getCoin());
				message.write("skill", baseTask.getSkill());
				message.write("receiver", baseTask.getReceiver());
				for (TaskAwardItem taskAwardItem : baseTask.getItemList()) {
					if (taskAwardItem.getProbability() >= 10000) {

						if (baseTask.getCategory().equals(ETaskCategory.guild)) {
							if (role.getGuildTaskSum() == 5) {
								LlpMessage msg = message.write("taskAwardItemList");
								msg.write("itemId", taskAwardItem.getItemId());
								msg.write("num", taskAwardItem.getNum());
							}
						} else {
							LlpMessage msg = message.write("taskAwardItemList");
							msg.write("itemId", taskAwardItem.getItemId());
							msg.write("num", taskAwardItem.getNum());
						}
					}
				}

				for (TaskAwardPartner taskAwardPartner : baseTask.getPartnerList()) {
					if (taskAwardPartner.getProbability() >= 10000) {
						LlpMessage msg = message.write("taskAwardPartnerList");
						msg.write("index", taskAwardPartner.getIndex());
						msg.write("num", taskAwardPartner.getNum());
					}
				}

				if (baseTask.getType().equals(ETaskType.collectionTask)) {
					CollectionTask task = (CollectionTask) baseTask;
					Map<Integer, Integer> needs = task.getNeeds();
					Set<Entry<Integer, Integer>> set = needs.entrySet();
					for (Iterator<Entry<Integer, Integer>> it = set.iterator(); it.hasNext();) {
						Entry<Integer, Integer> entry = it.next();
						LlpMessage msg = message.write("taskItemList");
						msg.write("itemId", entry.getKey());
						int num = Translate.integerToInt(task.getFinish().get(entry.getKey()));
						msg.write("itemNum", num);
						msg.write("itemMax", entry.getValue());
					}
				} else if (baseTask.getType().equals(ETaskType.killTask)) {
					KillTask task = (KillTask) baseTask;
					Map<Long, Integer> needs = task.getMonsters();
					Set<Entry<Long, Integer>> set = needs.entrySet();
					for (Iterator<Entry<Long, Integer>> it = set.iterator(); it.hasNext();) {
						Entry<Long, Integer> entry = it.next();
						LlpMessage msg = message.write("taskMonsterList");
						msg.write("monsterId", entry.getKey());
						int num = Translate.integerToInt(task.getFinish().get(entry.getKey()));
						msg.write("monsterNum", num);
						msg.write("monsterMax", entry.getValue());
					}
				} else if (baseTask.getType().equals(ETaskType.pickTask)) {
					PickTask task = (PickTask) baseTask;
					Map<Integer, Integer> needs = task.getNeeds();
					Set<Entry<Integer, Integer>> set = needs.entrySet();
					for (Iterator<Entry<Integer, Integer>> it = set.iterator(); it.hasNext();) {
						Entry<Integer, Integer> entry = it.next();
						LlpMessage msg = message.write("taskCollectedList");
						msg.write("itemId", entry.getKey());
						int num = Translate.integerToInt(task.getFinish().get(entry.getKey()));
						msg.write("itemNum", num);
						msg.write("itemMax", entry.getValue());
					}
				} else if (baseTask.getType().equals(ETaskType.yingxiongtieTask)) {
					YingxiongtieTask task = (YingxiongtieTask) baseTask;
					LlpMessage msg = message.write("yxtMonsterList");
					msg.write("monsterNum", task.getFinishNum());
					msg.write("monsterMax", task.getNeed());
				}
			}
		}
	}
}
