package iyunu.NewTLOL.json;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.TaskManager;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.res.TaskFblZoneRes;
import iyunu.NewTLOL.model.task.res.TaskGuildZoneRes;
import iyunu.NewTLOL.model.task.res.TaskRes;
import iyunu.NewTLOL.model.task.res.TaskZoneRes;
import iyunu.NewTLOL.model.task.taskCycle.instance.TaskCycleAward;
import iyunu.NewTLOL.model.task.taskCycle.instance.TaskCycleMsg;
import iyunu.NewTLOL.model.task.taskCycle.res.TaskCycleAwardRes;
import iyunu.NewTLOL.model.task.taskCycle.res.TaskCycleMsgRes;
import iyunu.NewTLOL.model.task.zone.TaskFblZone;
import iyunu.NewTLOL.model.task.zone.TaskGuildZone;
import iyunu.NewTLOL.util.Util;
import iyunu.NewTLOL.util.json.JsonImporter;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.ArrayListMultimap;

public final class TaskJson {

	/**
	 * 私有构造方法
	 */
	private TaskJson() {

	}

	private static TaskJson instance = new TaskJson();
	private static final String TASK_ZONE = "json/" + ServerManager.SERVER_RES + "/TaskZone.json.txt";
	private static final String TASK_MAIN = "json/" + ServerManager.SERVER_RES + "/TaskMain.json.txt";
	private static final String TASK_BRANCH = "json/" + ServerManager.SERVER_RES + "/TaskBranch.json.txt";
	private static final String TASK_STORY = "json/" + ServerManager.SERVER_RES + "/TaskStory.json.txt";
	private static final String TASK_GUILD = "json/" + ServerManager.SERVER_RES + "/TaskGuild.json.txt";
	private static final String TASK_GHOST = "json/" + ServerManager.SERVER_RES + "/TaskGhost.json.txt";
	private static final String TASK_CYCLE = "json/" + ServerManager.SERVER_RES + "/TaskCycle.json.txt";
	private static final String TASK_GUILD_ZONE = "json/" + ServerManager.SERVER_RES + "/TaskGuildZone.json.txt";
	private static final String TASK_YXT = "json/" + ServerManager.SERVER_RES + "/TaskYxt.json.txt";
	private static final String TASK_FBL = "json/" + ServerManager.SERVER_RES + "/TaskFbl.json.txt";
	private static final String TASK_FBL_ZONE = "json/" + ServerManager.SERVER_RES + "/TaskFblZone.json.txt";
	private static final String TASK_CYCLE_MSG = "json/" + ServerManager.SERVER_RES + "/TaskCycleMsg.json.txt";
	private static final String TASK_CYCLE_AWARD = "json/" + ServerManager.SERVER_RES + "/TaskCycleAward.json.txt";

	private Map<Integer, BaseTask> tasks = new HashMap<Integer, BaseTask>();
	private ArrayListMultimap<Integer,BaseTask> levelTasks = ArrayListMultimap.create(); // <等级，任务集合>
//	private Map<Integer, List<BaseTask>> levelTasks = new HashMap<Integer, List<BaseTask>>(); // <等级，任务集合>
	private ArrayListMultimap<Integer, BaseTask> zoneTasks = ArrayListMultimap.create(); // <任务区间，任务集合>
//	private Map<Integer, List<BaseTask>> zoneTasks = new HashMap<Integer, List<BaseTask>>(); // <任务区间，任务集合>
	private Map<Integer, BaseTask> taskGuilds = new HashMap<Integer, BaseTask>();
	private List<TaskGuildZone> taskGuildZones = new ArrayList<TaskGuildZone>();
	private Map<Integer, TaskFblZone> taskFblZones = new LinkedHashMap<Integer, TaskFblZone>();
	private List<TaskCycleMsg> taskCycleMsgs = new ArrayList<TaskCycleMsg>();
	private ArrayListMultimap<Integer, TaskCycleAward> taskCycleAwards = ArrayListMultimap.create();
//	private Map<Integer, List<TaskCycleAward>> taskCycleAwards = new HashMap<Integer, List<TaskCycleAward>>();

	/**
	 * 获取实例对象
	 * 
	 * @return 实例对象
	 */
	public static TaskJson instance() {
		return instance;
	}

	/**
	 * 数据重置
	 */
	private void clear() {
		tasks.clear();
		levelTasks.clear();
		zoneTasks.clear();
		taskGuilds.clear();
		taskGuildZones.clear();
		taskFblZones.clear();
		taskCycleMsgs.clear();
		taskCycleAwards.clear();
	}

	/**
	 * 初始化物品模板
	 */
	public void init() throws Exception {
		long start = System.currentTimeMillis();
		clear();

		List<TaskZoneRes> taskZoneList = JsonImporter.fileImporter(TASK_ZONE, new TypeReference<List<TaskZoneRes>>() {
		});
		for (TaskZoneRes taskZoneRes : taskZoneList) {
			switch (taskZoneRes.getZone()) {
			case 1:
				TaskManager.ZONE_1 = taskZoneRes.getMaxLevel();
				break;
			case 2:
				TaskManager.ZONE_2 = taskZoneRes.getMaxLevel();
				break;
			case 3:
				TaskManager.ZONE_3 = taskZoneRes.getMaxLevel();
				break;
			default:
				break;
			}
		}

		List<TaskRes> taskMainList = JsonImporter.fileImporter(TASK_MAIN, new TypeReference<List<TaskRes>>() {
		});
		for (TaskRes taskRes : taskMainList) {
			if (taskRes.getId() != 0) {
				BaseTask baseTask = taskRes.toTask();
				tasks.put(baseTask.getId(), baseTask);
			}
		}

		List<TaskRes> taskBranchList = JsonImporter.fileImporter(TASK_BRANCH, new TypeReference<List<TaskRes>>() {
		});
		for (TaskRes taskRes : taskBranchList) {
			if (taskRes.getId() != 0) {
				BaseTask baseTask = taskRes.toTask();
				tasks.put(baseTask.getId(), baseTask);

				levelTasks.put(baseTask.getLevel(), baseTask);
//				if (levelTasks.containsKey(baseTask.getLevel())) {
//					levelTasks.get(baseTask.getLevel()).add(baseTask);
//				} else {
//					List<BaseTask> list = new ArrayList<BaseTask>();
//					list.add(baseTask);
//					levelTasks.put(baseTask.getLevel(), list);
//				}
			}
		}

		List<TaskRes> taskStoryList = JsonImporter.fileImporter(TASK_STORY, new TypeReference<List<TaskRes>>() {
		});
		for (TaskRes taskRes : taskStoryList) {
			if (taskRes.getId() != 0) {
				BaseTask baseTask = taskRes.toTask();
				tasks.put(baseTask.getId(), baseTask);

				levelTasks.put(baseTask.getLevel(), baseTask);
				
//				if (levelTasks.containsKey(baseTask.getLevel())) {
//					levelTasks.get(baseTask.getLevel()).add(baseTask);
//				} else {
//					List<BaseTask> list = new ArrayList<BaseTask>();
//					list.add(baseTask);
//					levelTasks.put(baseTask.getLevel(), list);
//				}
			}
		}

		List<TaskRes> taskGuildList = JsonImporter.fileImporter(TASK_GUILD, new TypeReference<List<TaskRes>>() {
		});
		for (TaskRes taskRes : taskGuildList) {
			if (taskRes.getId() != 0) {
				BaseTask baseTask = taskRes.toTask();
				tasks.put(baseTask.getId(), baseTask);
				taskGuilds.put(baseTask.getId(), baseTask);
			}
		}

		List<TaskRes> taskGhostList = JsonImporter.fileImporter(TASK_GHOST, new TypeReference<List<TaskRes>>() {
		});
		for (TaskRes taskRes : taskGhostList) {
			if (taskRes.getId() != 0) {
				BaseTask baseTask = taskRes.toTask();
				tasks.put(baseTask.getId(), baseTask);
				zoneTasks.put(baseTask.getZone(), baseTask);
//				if (zoneTasks.containsKey(baseTask.getZone())) {
//					zoneTasks.get(baseTask.getZone()).add(baseTask);
//				} else {
//					List<BaseTask> list = new ArrayList<BaseTask>();
//					list.add(baseTask);
//					zoneTasks.put(baseTask.getZone(), list);
//				}
			}
		}

		List<TaskGuildZoneRes> taskGuildZoneList = JsonImporter.fileImporter(TASK_GUILD_ZONE, new TypeReference<List<TaskGuildZoneRes>>() {
		});
		for (TaskGuildZoneRes taskGuildZoneRes : taskGuildZoneList) {
			if (taskGuildZoneRes.getZone() != 0) {
				taskGuildZones.add(taskGuildZoneRes.toTaskGuildZone());
			}
		}

		List<TaskRes> taskYxtList = JsonImporter.fileImporter(TASK_YXT, new TypeReference<List<TaskRes>>() {
		});
		for (TaskRes taskRes : taskYxtList) {
			if (taskRes.getId() != 0) {
				BaseTask baseTask = taskRes.toTask();
				tasks.put(baseTask.getId(), baseTask);
			}
		}

		List<TaskRes> taskFblList = JsonImporter.fileImporter(TASK_FBL, new TypeReference<List<TaskRes>>() {
		});
		for (TaskRes taskRes : taskFblList) {
			if (taskRes.getId() != 0) {
				BaseTask baseTask = taskRes.toTask();
				tasks.put(baseTask.getId(), baseTask);
			}
		}

		List<TaskFblZoneRes> taskFblZoneList = JsonImporter.fileImporter(TASK_FBL_ZONE, new TypeReference<List<TaskFblZoneRes>>() {
		});
		for (TaskFblZoneRes taskFblZoneRes : taskFblZoneList) {
			if (taskFblZoneRes.getZone() != 0) {
				taskFblZones.put(taskFblZoneRes.getZone(), taskFblZoneRes.toTaskFblZone());
			}
		}

		List<TaskRes> taskCycleList = JsonImporter.fileImporter(TASK_CYCLE, new TypeReference<List<TaskRes>>() { // ============环任务==================
				});
		for (TaskRes taskRes : taskCycleList) {
			if (taskRes.getId() != 0) {
				BaseTask baseTask = taskRes.toTask();
				tasks.put(baseTask.getId(), baseTask);
			}
		}

		List<TaskCycleMsgRes> taskCycleMsgResList = JsonImporter.fileImporter(TASK_CYCLE_MSG, new TypeReference<List<TaskCycleMsgRes>>() {
		});
		for (TaskCycleMsgRes taskCycleMsgRes : taskCycleMsgResList) {
			taskCycleMsgs.add(taskCycleMsgRes.toTaskCycleMsg());
		}

		List<TaskCycleAwardRes> taskCycleAwardResList = JsonImporter.fileImporter(TASK_CYCLE_AWARD, new TypeReference<List<TaskCycleAwardRes>>() {
		});
		for (TaskCycleAwardRes taskCycleAwardRes : taskCycleAwardResList) {
			taskCycleAwards.put(taskCycleAwardRes.getNum(), taskCycleAwardRes.toTaskCycleAward());
//			if (taskCycleAwards.containsKey(taskCycleAwardRes.getNum())) {
//				taskCycleAwards.get(taskCycleAwardRes.getNum()).add(taskCycleAwardRes.toTaskCycleAward());
//			} else {
//				List<TaskCycleAward> list = new ArrayList<>();
//				list.add(taskCycleAwardRes.toTaskCycleAward());
//				taskCycleAwards.put(taskCycleAwardRes.getNum(), list);
//			}
		}

		long end = System.currentTimeMillis();
		System.out.println("任务脚本加载耗时：" + (end - start));
	}

	public TaskCycleAward getTaskCycleAward(int level, int num) {
		if (taskCycleAwards.containsKey(num)) {
			for (TaskCycleAward taskCycleAward : taskCycleAwards.get(num)) {
				if (taskCycleAward.getLevelMin() <= level && level <= taskCycleAward.getLevelMax()) {
					return taskCycleAward;
				}
			}
		}
		return null;
	}

	/**
	 * 获取环任务信息
	 * 
	 * @param level
	 * @return
	 */
	public TaskCycleMsg getTaskCycleMsg(int level) {
		for (TaskCycleMsg taskCycleMsg : taskCycleMsgs) {
			if (taskCycleMsg.getLevelMin() <= level && level <= taskCycleMsg.getLevelMax()) {
				return taskCycleMsg;
			}
		}
		return null;
	}

	/**
	 * 随机获取门派任务
	 * 
	 * @param 等级
	 * @return 门派任务
	 */
	public BaseTask randomGuildTask(int level) {
		for (TaskGuildZone taskGuildZone : taskGuildZones) {
			if (taskGuildZone.getMinLevel() <= level && level <= taskGuildZone.getMaxLevel()) {
				int taskId = taskGuildZone.getTaskIds().get(Util.getRandom(taskGuildZone.getTaskIds().size()));
				return taskGuilds.get(taskId).copy();
			}
		}
		return null;
	}

	/**
	 * 随机获取江湖追杀令
	 * 
	 * @return 江湖追杀令
	 */
	public BaseTask randomGhostTask(int zone) {
		if (zoneTasks.containsKey(zone)) {
			return zoneTasks.get(zone).get(Util.getRandom(zoneTasks.get(zone).size())).copy();
		}
		LogManager.exception("获取江湖追杀令任务异常：zone=" + zone);
		return null;
	}

	/**
	 * 任务是否存在
	 * 
	 * @param taskId
	 *            任务编号
	 * @return 任务存在
	 */
	public boolean exist(int taskId) {
		return tasks.containsKey(taskId);
	}

	/**
	 * 获取任务对象
	 * 
	 * @param taskId
	 *            任务编号
	 * @return 任务对象
	 */
	public BaseTask getTask(int taskId) {
		if (tasks.containsKey(taskId)) {
			return tasks.get(taskId).copy();
		}
		return null;
	}

	/**
	 * 通过等级获取任务
	 * 
	 * @param level
	 *            等级
	 * @return 任务集合
	 */
	public List<BaseTask> getTaskByLevel(int level) {
		if (levelTasks.containsKey(level)) {
			return levelTasks.get(level);
		}
		return new ArrayList<BaseTask>();
	}

	public TaskFblZone getTaskFblZone(int zone) {
		if (taskFblZones.containsKey(zone)) {
			return taskFblZones.get(zone);
		}
		return null;
	}

}
