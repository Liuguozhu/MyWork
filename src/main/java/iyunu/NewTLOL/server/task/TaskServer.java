package iyunu.NewTLOL.server.task;

import iyunu.NewTLOL.common.RoleForm;
import iyunu.NewTLOL.enumeration.common.EExp;
import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.enumeration.partner.EGetPartner;
import iyunu.NewTLOL.json.ItemJson;
import iyunu.NewTLOL.json.PartnerJson;
import iyunu.NewTLOL.json.TaskJson;
import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.manager.PartnerManager;
import iyunu.NewTLOL.manager.TaskManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.message.TaskMessage;
import iyunu.NewTLOL.model.activity.fbl.ActivityFblInfo;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.helper.EHelper;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.EItemGet;
import iyunu.NewTLOL.model.item.EOpen;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.map.EMonsterOnMap;
import iyunu.NewTLOL.model.map.MonsterOnMap;
import iyunu.NewTLOL.model.partner.EpartnerOperate;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.ETaskCategory;
import iyunu.NewTLOL.model.task.ETaskState;
import iyunu.NewTLOL.model.task.ETaskType;
import iyunu.NewTLOL.model.task.TaskAwardItem;
import iyunu.NewTLOL.model.task.TaskAwardPartner;
import iyunu.NewTLOL.model.task.instance.BattleOnMapTask;
import iyunu.NewTLOL.model.task.instance.CollectionTask;
import iyunu.NewTLOL.model.task.instance.PickTask;
import iyunu.NewTLOL.model.task.taskCycle.instance.TaskCycleAward;
import iyunu.NewTLOL.model.task.taskCycle.instance.TaskCycleMsg;
import iyunu.NewTLOL.server.award.AwardServer;
import iyunu.NewTLOL.server.bag.BagServer;
import iyunu.NewTLOL.server.helper.HelperServer;
import iyunu.NewTLOL.server.mail.MailServer;
import iyunu.NewTLOL.server.partner.PartnerServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.Util;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TaskServer {

	/**
	 * 完成指定类型任务
	 * 
	 * @param role
	 *            角色对象
	 * @param type
	 *            任务类型
	 */
	public static void finishTaskByType(Role role, ETaskType type) {
		for (BaseTask baseTask : role.getTasks().values()) {
			if (baseTask.getType().equals(type)) {
				baseTask.finishTask();
			}
		}
	}

	/**
	 * 完成指定任务任务
	 * 
	 * @param role
	 *            角色对象
	 * @param taskId
	 *            任务编号
	 */
	public static void finishTaskById(Role role, int taskId) {
		if (role.getTasks().containsKey(taskId)) {
			role.getTasks().get(taskId).finishTask();
		}
	}

	/**
	 * 检查任务是否已完成
	 * 
	 * @param role
	 *            角色对象
	 * @param taskId
	 *            任务编号
	 * @return 任务已完成
	 */
	public static boolean checkFinishTask(Role role, int taskId) {
		return (role.getFinishTasks().contains(taskId) || taskId == 0);
	}

	/**
	 * 检查任务是否存在
	 * 
	 * @param role
	 *            角色对象
	 * @param taskId
	 *            任务编号
	 * @return 任务已存在
	 */
	public static boolean checkTask(Role role, int taskId) {
		return role.getTasks().containsKey(taskId);
	}

	/**
	 * 检查任务是否已接
	 * 
	 * @param role
	 *            角色对象
	 * @param taskId
	 *            任务编号
	 * @return 任务已接
	 */
	public static boolean chechTaskGot(Role role, int taskId) {
		if (role.getTasks().containsKey(taskId)) {
			return role.getTasks().get(taskId).getState().equals(ETaskState.during);
		}
		return false;
	}

	/**
	 * 刷新角色任务
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshTask(Role role) {
		int level = role.getLevel();
		List<BaseTask> baseTasks = TaskJson.instance().getTaskByLevel(level);
		for (BaseTask baseTask : baseTasks) {
			BaseTask task = baseTask.copy();
			if (!role.getFinishTasks().contains(task.getId()) && !checkTask(role, task.getId())) {
				if (checkFinishTask(role, task.getPrecondition())) {
					task.setState(ETaskState.not);
				} else {
					task.setState(ETaskState.none);
				}
				role.getTasks().put(task.getId(), task);
				task.setRole(role);
			}
		}
	}

	/**
	 * 检查任务状态
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void checkTaskState(Role role) {
		for (BaseTask task : role.getTasks().values()) {

			if (task.getState().equals(ETaskState.none)) {
				if (checkFinishTask(role, task.getPrecondition())) {
					task.setState(ETaskState.not);
				}
			}

			switch (task.getType()) {
			case killTask:
			case collectionTask:
				if (task.getState().equals(ETaskState.during)) {
					task.finishTask();
				}
				break;

			case finishRaidsTask:
			case finishGuildTask:
			case finishGhostTask:
			case intoRaids:
			case stoneMakeTask:
			case npcFightTask:
				break;
			default:
				task.finishTask();
				break;
			}
		}
	}

	/**
	 * 检查任务
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void checkTask(Role role) {

		boolean isHaveFBL = false;
		Iterator<BaseTask> taskIt = role.getTasks().values().iterator();
		while (taskIt.hasNext()) {
			BaseTask task = taskIt.next();

			task.setRole(role);

			if (task.getState().equals(ETaskState.none)) {
				if (checkFinishTask(role, task.getPrecondition())) {
					task.setState(ETaskState.not);
				}
			}

			if (task.getCategory().equals(ETaskCategory.guild)) {
				int n = role.getGuildTaskNum() % 10;
				int exp = RoleForm.taskExp(role.getLevel(), n);
				int gold = RoleForm.taskGold(role.getLevel(), n);
				if (role.getGuildTaskSum() < 20) {
					exp = exp * 2;
					gold = gold * 2;
				}
				task.setExp(exp);
				task.setGold(gold);
				role.setGuildTask(task);
			} else if (task.getCategory().equals(ETaskCategory.ghost)) {
				task.getItemList().clear();
				task.getPartnerList().clear();
				TaskAwardItem taskAwardItem = TaskServer.getGhostTaskAward(role.getGhostTaskSum());
				if (taskAwardItem != null) {
					task.getItemList().add(taskAwardItem);
				}
				task.setGold(RoleForm.ghostTaskGold(role.getLevel()));
				task.setExp(RoleForm.ghostTaskExp(role.getLevel()));
				role.setGhostTask(task);
			} else if (task.getCategory().equals(ETaskCategory.yingxiongtie)) {
				role.setYingxiongtie(task);
			} else if (task.getCategory().equals(ETaskCategory.fabuling)) {
				// 判断是否到期
				isHaveFBL = true;
				if (task.getTimeOut() <= System.currentTimeMillis()) {
					task.setState(ETaskState.failed);
				} else {
					if (ActivityManager.activityFblInfos.containsKey(role.getTaskFblId())) {
						ActivityFblInfo activityFblInfo = ActivityManager.activityFblInfos.get(role.getTaskFblId());
						if (activityFblInfo.getState() == 1 && activityFblInfo.getReceverId() == role.getId()) {
							task.setCoin(activityFblInfo.getCoin());
							role.setFabuling(activityFblInfo);
						}
					}
				}
			} else if (task.getCategory().equals(ETaskCategory.cycle)) {

				int exp = RoleForm.taskCycleExp(role.getLevel());
				task.setExp(exp);
				role.setTaskCycle(task);
			}

			switch (task.getType()) {
			case collectionTask:
				if (task.getState().equals(ETaskState.during) || task.getState().equals(ETaskState.finish)) {
					CollectionTask collectionTask = (CollectionTask) task;
					collectionTask.clearFinish();
					TaskServer.checkCollectionTask(role, collectionTask);
				}
				break;
			case finishRaidsTask:
			case finishGuildTask:
			case finishGhostTask:
				break;
			default:
				task.finishTask();
				break;
			}
		}

		if (!isHaveFBL) {
			role.setFabuling(null);
			role.setTaskFblId(0);
		}
	}

	/**
	 * 接取任务
	 * 
	 * @param task
	 *            任务
	 * @param role
	 *            角色地图
	 */
	public static void getTask(BaseTask task, Role role) {
		task.setRole(role);
		switch (task.getType()) {
		case dialogueTask:
			task.setState(ETaskState.finish);
			break;
		case collectionTask:
			task.setState(ETaskState.during);
			CollectionTask collectionTask = (CollectionTask) task;
			collectionTask.clearFinish();
			TaskServer.checkCollectionTask(role, collectionTask);
			break;
		case battleOnMapTask:
			BattleOnMapTask bTask = (BattleOnMapTask) task;
			MonsterOnMap monsterOnMap = new MonsterOnMap(bTask.getArriveMap(), bTask.getArriveX(), bTask.getArriveY(), bTask.getMonsterGroup(), EMonsterOnMap.none);
			monsterOnMap.setTaskCategory(task.getCategory().ordinal()); // 设置绑定任务类型
			bTask.setUid(monsterOnMap.getUid());
			role.getMapInfo().addMonsterOnMap(monsterOnMap);
			task.setState(ETaskState.during);
			break;
		case getPartnerTask:
		case joinGuildTask:
		case bodyIntensifyTask:
			task.setState(ETaskState.during);
			task.finishTask();
			break;
		case jionGangTask:
			task.setState(ETaskState.during);
			task.finishTask();
			break;
		default:
			task.setState(ETaskState.during);
			break;
		}

	}

	/**
	 * 完成任务
	 * 
	 * @param role
	 *            角色对象
	 * @param task
	 *            任务对象
	 */
	public static void finishTask(Role role, BaseTask task) {

		if (task.getState().equals(ETaskState.finish)) {
			LogManager.taskLog(role, task.getId(), 1); // 游戏日志
			role.getTasks().remove(task.getId());

			int gold = task.getGold();
			// 增加技能点数
			RoleServer.addSkill(role, task.getSkill());

			Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();

			if (task.getCategory().equals(ETaskCategory.ghost)) { // ==========================江湖追杀令==========================
				role.setGhostTask(null);

				gold += gold * role.getVip().getLevel().getGhostGoldAdd();

				if (role.getGhostTaskNum() % TaskManager.GHOST_TASK_NUM == 0) { // 第十个有奖励
					// 任务奖励
					for (TaskAwardItem taskAwardItem : task.getItemList()) {
						Item item = ItemJson.instance().getItem(taskAwardItem.getItemId());
						item.setIsDeal(taskAwardItem.getIsBind());
						BagServer.add(role, item, taskAwardItem.getNum(), cellsMap, EItemGet.task);
					}

					for (TaskAwardPartner taskAwardPartner : task.getPartnerList()) {
						if (Util.probable(taskAwardPartner.getProbability())) {
							Partner newPartner = PartnerJson.instance().getNewPartner(taskAwardPartner.getIndex());
							newPartner = PartnerServer.addPartner(role, newPartner, EGetPartner.monster);
							newPartner.setIsBind(taskAwardPartner.getIsBind());

							// 背包已满或已达到携带伙伴上限,邮件下发
							if (role.getPartnerMap().size() >= PartnerManager.MAX_NUM) {
								MailServer.send(role.getId(), "系统邮件", "任务获得伙伴", null, 0, 0, 0, 0, newPartner);
							} else {
								List<Partner> partnerList = new ArrayList<>();
								newPartner.setOperateFlag(EpartnerOperate.add);
								partnerList.add(newPartner);
								PartnerMessage.sendPartners(role, partnerList);

								AwardServer.addPartner(role, newPartner);
							}
						}
					}

					// TODO 检查任务
					TaskServer.finishTaskByType(role, ETaskType.finishGhostTask);
				}

				TaskServer.addGhostTaskSum(role, 1); // 任务完成，总次数+1
				TaskServer.addGhostTaskNum(role); // 任务完成，总次数+1
				HelperServer.helper(role, EHelper.ghost); // 小助手记录
			} else if (task.getCategory().equals(ETaskCategory.yingxiongtie)) { // ==========================英雄贴任务==========================
				if (role.getYxtTaskNum() < role.getVip().getLevel().getYingxiongtieAdd() + 5) {
					role.setYingxiongtie(null);
					role.setYxtTaskNum(role.getYxtTaskNum() + 1);

					HelperServer.helper(role, EHelper.yingxiong); // 小助手记录
				}
			} else if (task.getCategory().equals(ETaskCategory.fabuling)) { // ==========================发布令任务==========================

				// 增加银两
				RoleServer.addCoin(role, task.getCoin(), EGold.fabulingAward);

				TaskManager.fblFinish(role.getFabuling()); // 完成发布令任务

				role.setFabuling(null);
				role.setTaskFblId(0);

			} else if (task.getCategory().equals(ETaskCategory.cycle)) { // ==========================环任务==========================

				// 任务奖励
				if (task.getReward().equals(EOpen.all)) {
					for (TaskAwardItem taskAwardItem : task.getItemList()) {
						if (Util.probable(taskAwardItem.getProbability())) {
							Item item = ItemJson.instance().getItem(taskAwardItem.getItemId());
							item.setIsDeal(taskAwardItem.getIsBind());
							BagServer.add(role, item, taskAwardItem.getNum(), cellsMap, EItemGet.task);
						}
					}
				} else {
					for (TaskAwardItem taskAwardItem : task.getItemList()) {
						if (Util.probable(taskAwardItem.getProbability())) {
							Item item = ItemJson.instance().getItem(taskAwardItem.getItemId());
							item.setIsDeal(taskAwardItem.getIsBind());
							BagServer.add(role, item, taskAwardItem.getNum(), cellsMap, EItemGet.task);
							break;
						}
					}
				}

				for (TaskAwardPartner taskAwardPartner : task.getPartnerList()) {
					for (int i = 0; i < taskAwardPartner.getNum(); i++) {
						if (Util.probable(taskAwardPartner.getProbability())) {

							Partner newPartner = PartnerJson.instance().getNewPartner(taskAwardPartner.getIndex());
							newPartner = PartnerServer.addPartner(role, newPartner, EGetPartner.monster);
							newPartner.setIsBind(taskAwardPartner.getIsBind());

							// 背包已满或已达到携带伙伴上限,邮件下发
							if (role.getPartnerMap().size() >= PartnerManager.MAX_NUM) {
								MailServer.send(role.getId(), "系统邮件", "任务获得伙伴", null, 0, 0, 0, 0, newPartner);
							} else {
								List<Partner> partnerList = new ArrayList<>();
								newPartner.setOperateFlag(EpartnerOperate.add);
								partnerList.add(newPartner);
								PartnerMessage.sendPartners(role, partnerList);

								AwardServer.addPartner(role, newPartner);
							}
						}
					}
				}

				if (role.getTaskCycleNum() < TaskManager.CYCLE_TASK_NUM) {
					TaskServer.getTaskCycle(role);
				} else {
					role.setTaskCycle(null);
					role.setTaskCycleNum(0);
				}
				HelperServer.helper(role, EHelper.taskCycle); // 小助手记录
			} else { // ==========================其他==========================

				// 将任务加到完成任务列表中
				role.getFinishTasks().add(task.getId());

				// 任务奖励
				if (task.getReward().equals(EOpen.all)) {
					for (TaskAwardItem taskAwardItem : task.getItemList()) {
						if (Util.probable(taskAwardItem.getProbability())) {
							Item item = ItemJson.instance().getItem(taskAwardItem.getItemId());
							item.setIsDeal(taskAwardItem.getIsBind());
							BagServer.add(role, item, taskAwardItem.getNum(), cellsMap, EItemGet.task);
						}
					}
				} else {
					for (TaskAwardItem taskAwardItem : task.getItemList()) {
						if (Util.probable(taskAwardItem.getProbability())) {
							Item item = ItemJson.instance().getItem(taskAwardItem.getItemId());
							item.setIsDeal(taskAwardItem.getIsBind());
							BagServer.add(role, item, taskAwardItem.getNum(), cellsMap, EItemGet.task);
							break;
						}
					}
				}

				for (TaskAwardPartner taskAwardPartner : task.getPartnerList()) {
					for (int i = 0; i < taskAwardPartner.getNum(); i++) {
						if (Util.probable(taskAwardPartner.getProbability())) {

							Partner newPartner = PartnerJson.instance().getNewPartner(taskAwardPartner.getIndex());
							newPartner = PartnerServer.addPartner(role, newPartner, EGetPartner.monster);
							newPartner.setIsBind(taskAwardPartner.getIsBind());

							// 背包已满或已达到携带伙伴上限,邮件下发
							if (role.getPartnerMap().size() >= PartnerManager.MAX_NUM) {
								MailServer.send(role.getId(), "系统邮件", "任务获得伙伴", null, 0, 0, 0, 0, newPartner);
							} else {
								List<Partner> partnerList = new ArrayList<>();
								newPartner.setOperateFlag(EpartnerOperate.add);
								partnerList.add(newPartner);
								PartnerMessage.sendPartners(role, partnerList);

								AwardServer.addPartner(role, newPartner);
							}
						}
					}
				}
			}

			// 增加绑银
			RoleServer.addGold(role, gold, EGold.task);

			if (task.getType().equals(ETaskType.collectionTask)) {
				CollectionTask collectionTask = (CollectionTask) task;
				Set<Entry<Integer, Integer>> set = collectionTask.getNeeds().entrySet();
				for (Iterator<Entry<Integer, Integer>> it = set.iterator(); it.hasNext();) {
					Entry<Integer, Integer> entry = it.next();
					boolean result = role.getBag().removeById(entry.getKey(), entry.getValue(), cellsMap, EItemCost.task);
					if (!result) {
						role.getBagStone().removeById(entry.getKey(), entry.getValue(), cellsMap, EItemCost.task);
					}
				}
			} else if (task.getType().equals(ETaskType.pickTask)) {
				PickTask pickTask = (PickTask) task;
				Set<Entry<Integer, Integer>> set = pickTask.getNeeds().entrySet();
				for (Iterator<Entry<Integer, Integer>> it = set.iterator(); it.hasNext();) {
					Entry<Integer, Integer> entry = it.next();
					role.getBag().removeById(entry.getKey(), entry.getValue(), cellsMap, EItemCost.task);
				}
			}

			if (!cellsMap.isEmpty()) {
				BagMessage.sendBag(role, cellsMap);
			}

			if (task.getCategory().equals(ETaskCategory.main)) {
				BaseTask newTask = TaskJson.instance().getTask(task.getNext());
				if (newTask != null) {
					newTask.setState(ETaskState.not);
					newTask.setRole(role);
					role.getTasks().put(newTask.getId(), newTask);
				}
			}

			// 增加经验
			RoleServer.addExp(role, task.getExp(), EExp.task);
			checkTaskState(role);
		}
	}

	/**
	 * 增加江湖追杀令次数
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void addGhostTaskNum(Role role) {
		int num = role.getGhostTaskNum();
		if (num >= 10) {
			role.setGhostTaskNum(1);
		} else {
			role.setGhostTaskNum(num + 1);
		}
	}

	public static void addGhostTaskSum(Role role, int num) {
		role.setGhostTaskSum(role.getGhostTaskSum() + num);
	}

	/**
	 * 增加门派任务次数
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void addGuildTaskNum(Role role) {
		int num = role.getGuildTaskNum();
		if (num >= TaskManager.GUILD_TASK_NUM) {
			role.setGuildTaskNum(1);
		} else {
			role.setGuildTaskNum(num + 1);
		}
	}

	public static void addGuildTaskSum(Role role) {
		role.setGuildTaskSum(role.getGuildTaskSum() + 1);
	}

	/**
	 * 设置门派任务次数
	 * 
	 * @param role
	 *            角色对象
	 * @param num
	 *            门派任务次数
	 */
	public static void setGuildtTaskNum(Role role, int num) {
		role.setGuildTaskNum(num);
	}

	/**
	 * 设置江湖追杀令次数
	 * 
	 * @param role
	 *            角色对象
	 * @param num
	 *            江湖追杀令次数
	 */
	public static void setGhostTaskNum(Role role, int num) {
		role.setGhostTaskNum(num);
	}

	/**
	 * 设置门派任务总次数
	 * 
	 * @param role
	 *            角色对象
	 * @param num
	 *            门派任务总次数
	 */
	public static void setGuildtTaskSum(Role role, int num) {
		role.setGuildTaskSum(num);
	}

	/**
	 * 设置江湖追杀令总次数
	 * 
	 * @param role
	 *            角色对象
	 * @param num
	 *            江湖追杀令总次数
	 */
	public static void setGhostTaskSum(Role role, int num) {
		role.setGhostTaskSum(num);
	}

	public static int getGuildTask(Role role) {
		BaseTask task = TaskJson.instance().randomGuildTask(role.getLevel());
		int n = role.getGuildTaskNum() % TaskManager.GUILD_TASK_NUM + 1;
		int exp = RoleForm.taskExp(role.getLevel(), n);
		int gold = RoleForm.taskGold(role.getLevel(), n);

		if (role.getGuildTaskSum() < 5) { // 每日前5个双倍奖励
			exp = exp * 2;
			gold = gold * 2;
		}

		task.setExp(exp);
		task.setGold(gold);
		task.setRole(role);
		switch (task.getType()) {
		case dialogueTask:
			task.setState(ETaskState.finish);
			break;
		case collectionTask:
			task.setState(ETaskState.during);
			CollectionTask collectionTask = (CollectionTask) task;
			collectionTask.clearFinish();
			TaskServer.checkCollectionTask(role, collectionTask);
			break;
		case battleOnMapTask:
			task.setState(ETaskState.during);
			BattleOnMapTask bTask = (BattleOnMapTask) task;
			MonsterOnMap monsterOnMap = new MonsterOnMap(bTask.getArriveMap(), bTask.getArriveX(), bTask.getArriveY(), bTask.getMonsterGroup(), EMonsterOnMap.taskGuild);
			bTask.setUid(monsterOnMap.getUid());
			role.getMapInfo().addMonsterOnMap(monsterOnMap);
			break;
		case getPartnerTask:
		case bodyIntensifyTask:
			task.finishTask();
			break;
		default:
			task.setState(ETaskState.during);
			break;
		}

		role.getTasks().put(task.getId(), task);

		role.setGuildTask(task);

		TaskServer.addGuildTaskNum(role); // 门派任务次数
		TaskServer.addGuildTaskSum(role); // 任务完成，总次数+1

		TaskMessage.refreshGuildTaskNum(role);

		return task.getId();
	}

	public static TaskAwardItem getGhostTaskAward(int n) {
		int itemId = 50019;
		switch (n) {
		case 100:
		case 90:
			itemId = 50023;
			return new TaskAwardItem(itemId, 1, 10000, 0);
		case 80:
		case 70:
			itemId = 50022;
			return new TaskAwardItem(itemId, 1, 10000, 0);
		case 60:
		case 50:
			itemId = 50021;
			return new TaskAwardItem(itemId, 1, 10000, 0);
		case 40:
		case 30:
			itemId = 50020;
			return new TaskAwardItem(itemId, 1, 10000, 0);
		case 20:
		case 10:
			itemId = 50019;
			return new TaskAwardItem(itemId, 1, 10000, 0);
		default:
			return null;
		}
	}

	public static int getTaskCycle(Role role) {
		int num = role.getTaskCycleNum() + 1;
		role.setTaskCycleNum(num);

		TaskCycleMsg taskCycleMsg = TaskJson.instance().getTaskCycleMsg(role.getLevel());

		boolean isChuanshuo = taskCycleMsg.isChuanshuo(num);
		int npcId = taskCycleMsg.randomNpc();
		int taskId = 0;
		if (isChuanshuo) {
			taskId = taskCycleMsg.randomChuanshuoTask();
		} else {
			taskId = taskCycleMsg.randomTask();
		}
		BaseTask task = TaskJson.instance().getTask(taskId);

		int exp = RoleForm.taskCycleExp(role.getLevel());
		task.setExp(exp);
		task.setRole(role);
		task.setReceiver(npcId);

		TaskCycleAward taskCycleAward = TaskJson.instance().getTaskCycleAward(role.getLevel(), num);
		if (taskCycleAward != null) {
			for (TaskAwardItem taskAwardItem : taskCycleAward.getItem()) {
				task.getItemList().add(taskAwardItem);
			}

			for (TaskAwardPartner taskAwardPartner : taskCycleAward.getPartner()) {
				task.getPartnerList().add(taskAwardPartner);
			}
		}
		switch (task.getType()) {
		case dialogueTask:
			task.setState(ETaskState.finish);
			break;
		case collectionTask:
			task.setState(ETaskState.during);
			CollectionTask collectionTask = (CollectionTask) task;
			collectionTask.setChuanshuo(isChuanshuo); // 设置为传说任务
			collectionTask.clearFinish();

			TaskServer.checkCollectionTask(role, collectionTask);
			break;
		case battleOnMapTask:
			task.setState(ETaskState.during);
			BattleOnMapTask bTask = (BattleOnMapTask) task;
			MonsterOnMap monsterOnMap = new MonsterOnMap(bTask.getArriveMap(), bTask.getArriveX(), bTask.getArriveY(), bTask.getMonsterGroup(), EMonsterOnMap.taskCycle);
			bTask.setUid(monsterOnMap.getUid());
			role.getMapInfo().addMonsterOnMap(monsterOnMap);
			break;
		case getPartnerTask:
		case bodyIntensifyTask:
			task.finishTask();
			break;
		default:
			task.setState(ETaskState.during);
			break;
		}

		role.getTasks().put(task.getId(), task);
		role.setTaskCycle(task);

		TaskMessage.refreshTaskCycleNum(role);
		return taskId;
	}

	public static void checkCollectionTask(Role role, CollectionTask collectionTask) {
		Cell[] cells = role.getBag().getCells();
		for (int i = 0; i < cells.length; i++) {
			Item item = cells[i].getItem();
			if (item != null && collectionTask.check(item.getId())) {
				collectionTask.add(item.getId(), cells[i].getNum());
			}
		}

		Cell[] stoneCells = role.getBagStone().getCells();
		for (int i = 0; i < stoneCells.length; i++) {
			Item item = stoneCells[i].getItem();
			if (item != null && collectionTask.check(item.getId())) {
				collectionTask.add(item.getId(), stoneCells[i].getNum());
			}
		}
	}
}
