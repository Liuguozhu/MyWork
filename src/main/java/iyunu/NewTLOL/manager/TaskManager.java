package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.model.activity.fbl.ActivityFblInfo;
import iyunu.NewTLOL.model.daily.DailyModelRole;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.ETaskState;
import iyunu.NewTLOL.model.task.instance.CollectionTask;
import iyunu.NewTLOL.server.mail.MailServer;

import java.util.List;
import java.util.Map;

public class TaskManager {
	/**
	 * 私有构造方法
	 */
	private TaskManager() {
	}

	private static TaskManager instance = new TaskManager();

	/**
	 * 获取TaskManager对象
	 * 
	 * @return TaskManager对象
	 */
	public static TaskManager instance() {
		return instance;
	}

	/** 门派任务 **/
	public static final int GUILD_TASK_LEVEL = 20;
	/** 门派任务最大数 **/
	public static final int GUILD_TASK_MAX = 10;
	/** 门派任务每轮数量 **/
	public static final int GUILD_TASK_NUM = 5;
	/** 江湖追杀令任务最大数 **/
	public static final int GHOST_TASK_MAX = 100;
	/** 江湖追杀令任务每轮数量 **/
	public static final int GHOST_TASK_NUM = 10;
	/** 发布令任务每日数量 **/
	public static final int FBL_TASK_NUM = 5;
	/** 发布令任务总数量 **/
	public static final int FBL_TASK_SUM = 10;
	/** 最大环任务轮数 **/
	public static final int CYCLE_TASK_MAX = 5;
	/** 每轮环任务最大数 **/
	public static final int CYCLE_TASK_NUM = 300;

	public static int ZONE_1 = 50;
	public static int ZONE_2 = 60;
	public static int ZONE_3 = 70;

	public static int getZoneByLevel(int level) {
		if (level <= ZONE_1) {
			return 1;
		} else if (level <= ZONE_2) {
			return 2;
		} else if (level <= ZONE_3) {
			return 3;
		} else {
			return 4;
		}
	}

	/**
	 * 增加收集任务物品
	 * 
	 * @param role
	 *            角色对象
	 * @param item
	 *            物品对象
	 * @param num
	 *            数量
	 */
	public static void addCollectionTaskItem(Role role, Item item, int num) {

		// -----------------------------------------------------------每日任务------------------------------------------------

		try {
			Map<Integer, List<Integer>> dailyMonster = DailyManager.instance().getDailyItemMap();
			if (dailyMonster.containsKey(item.getId())) {
				List<Integer> dailyMonsterList = dailyMonster.get(item.getId());
				for (Integer integer : dailyMonsterList) {
					if (DailyManager.instance().checkEvent(integer)) {
						DailyModelRole dailyModelRole = role.getDailyMap().get(integer);
						DailyManager.instance().finishDailyItem(dailyModelRole, role);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// -----------------------------------------------------------每日任务------------------------------------------------
		for (Integer taskId : item.getTaskIds()) {
			if (role.getTasks().containsKey(taskId)) {
				CollectionTask collectionTask = (CollectionTask) role.getTasks().get(taskId);
				if (collectionTask.getState().equals(ETaskState.during)) {
					collectionTask.add(item.getId(), num);
				}
			}
		}
	}

	/**
	 * 减少收集任务物品
	 * 
	 * @param role
	 *            角色对象
	 * @param item
	 *            物品对象
	 * @param num
	 *            数量
	 */
	public static void deleteCollectionTaskItem(Role role, Item item, int num) {
		// -----------------------------------------------------------每日任务------------------------------------------------
		try {
			Map<Integer, List<Integer>> dailyMonster = DailyManager.instance().getDailyItemMap();
			if (dailyMonster.containsKey(item.getId())) {
				List<Integer> dailyMonsterList = dailyMonster.get(item.getId());
				for (Integer integer : dailyMonsterList) {
					if (DailyManager.instance().checkEvent(integer)) {
						DailyModelRole dailyModelRole = role.getDailyMap().get(integer);
						DailyManager.instance().finishDailyItem(dailyModelRole, role);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// -----------------------------------------------------------每日任务------------------------------------------------

		for (Integer taskId : item.getTaskIds()) {
			if (role.getTasks().containsKey(taskId)) {
				CollectionTask collectionTask = (CollectionTask) role.getTasks().get(taskId);
				if (collectionTask.getState().equals(ETaskState.during) || collectionTask.getState().equals(ETaskState.finish)) {
					collectionTask.delete(item.getId(), num);
				}
			}
		}
	}

	/**
	 * 检查收集任务
	 * 
	 * @param role
	 * @param item
	 * @return
	 */
	public static boolean checkCollectionTaskItem(Role role, Item item) {
		for (Integer taskId : item.getTaskIds()) {
			if (role.getTasks().containsKey(taskId)) {
				BaseTask baseTask = role.getTasks().get(taskId);
				if (baseTask.getState().equals(ETaskState.during)) {
					if (baseTask instanceof CollectionTask) {
						CollectionTask collectionTask = (CollectionTask) baseTask;
						if (!collectionTask.isChuanshuo() || !collectionTask.isGet()) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public static void fblFinish(ActivityFblInfo activityFblInfo) {
		// 任务状态变为已完成
		activityFblInfo.setState(2);
		ActivityManager.cancelActivityFbl(activityFblInfo); // 取消发布令
		// 发送邮件
		MailServer.send(activityFblInfo.getRoleId(), "发布令奖励", "您发布的任务已被完成，获得" + activityFblInfo.getExp() + "经验奖励", null, 0, 0, 0, activityFblInfo.getExp(), 0, null);
	}
}
