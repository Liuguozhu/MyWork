package iyunu.NewTLOL.util.export;

import iyunu.NewTLOL.model.task.res.TaskFblZoneRes;
import iyunu.NewTLOL.model.task.res.TaskGuildZoneRes;
import iyunu.NewTLOL.model.task.res.TaskRes;
import iyunu.NewTLOL.model.task.res.TaskYxtZoneRes;
import iyunu.NewTLOL.model.task.res.TaskZoneRes;
import iyunu.NewTLOL.model.task.taskCycle.res.TaskCycleAwardRes;
import iyunu.NewTLOL.model.task.taskCycle.res.TaskCycleMsgRes;
import iyunu.NewTLOL.util.json.JsonExproter;

public final class TaskConverter {

	/**
	 * 任务资源转换器
	 */
	public static void converter(String serverRes) {
		String xlsxFile = "docs/资源文档/" +serverRes + "/任务.xlsx";
		
		String taskZone = "src/main/resources/json/" + serverRes + "/TaskZone.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, taskZone, TaskZoneRes.class, "任务级别区间");
		String taskMain = "src/main/resources/json/" + serverRes + "/TaskMain.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, taskMain, TaskRes.class, "主线任务");
		String taskBranch = "src/main/resources/json/" + serverRes + "/TaskBranch.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, taskBranch, TaskRes.class, "支线任务");
		String taskStory = "src/main/resources/json/" + serverRes + "/TaskStory.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, taskStory, TaskRes.class, "剧情任务");
		String taskGuild = "src/main/resources/json/" + serverRes + "/TaskGuild.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, taskGuild, TaskRes.class, "门派任务");
		String taskGhost = "src/main/resources/json/" + serverRes + "/TaskGhost.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, taskGhost, TaskRes.class, "江湖追杀令");

		String taskFbl = "src/main/resources/json/" + serverRes + "/TaskFbl.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, taskFbl, TaskRes.class, "发布令任务");
		
		String taskCycle = "src/main/resources/json/" + serverRes + "/TaskCycle.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, taskCycle, TaskRes.class, "环任务");

		String taskGuildZone = "src/main/resources/json/" + serverRes + "/TaskGuildZone.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, taskGuildZone, TaskGuildZoneRes.class, "门派任务区间");
		String taskYxt = "src/main/resources/json/" + serverRes + "/TaskYxt.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, taskYxt, TaskRes.class, "英雄帖");
		String taskYxtZone = "src/main/resources/json/" + serverRes + "/TaskYxtZone.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, taskYxtZone, TaskYxtZoneRes.class, "英雄帖怪物区间");

		String taskFblZone = "src/main/resources/json/" + serverRes + "/TaskFblZone.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, taskFblZone, TaskFblZoneRes.class, "发布令任务区间");
		
		String taskCycleMsg = "src/main/resources/json/" + serverRes + "/TaskCycleMsg.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, taskCycleMsg, TaskCycleMsgRes.class, "环任务信息");
		
		String taskCycleAward = "src/main/resources/json/" + serverRes + "/TaskCycleAward.json.txt";
		JsonExproter.convertToJsonFile(xlsxFile, taskCycleAward, TaskCycleAwardRes.class, "环任务奖励");
	}

}