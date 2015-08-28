package iyunu.NewTLOL.model.task;

import iyunu.NewTLOL.model.task.instance.BattleOnMapTask;
import iyunu.NewTLOL.model.task.instance.BodyIntensifyTask;
import iyunu.NewTLOL.model.task.instance.CollectionTask;
import iyunu.NewTLOL.model.task.instance.DialogueTask;
import iyunu.NewTLOL.model.task.instance.FinishGhostTask;
import iyunu.NewTLOL.model.task.instance.FinishGuildTask;
import iyunu.NewTLOL.model.task.instance.FinishRaidsTask;
import iyunu.NewTLOL.model.task.instance.GetPartnerTask;
import iyunu.NewTLOL.model.task.instance.GhostTask;
import iyunu.NewTLOL.model.task.instance.IntoRaidsTask;
import iyunu.NewTLOL.model.task.instance.JionGangTask;
import iyunu.NewTLOL.model.task.instance.JoinGuildTask;
import iyunu.NewTLOL.model.task.instance.KillTask;
import iyunu.NewTLOL.model.task.instance.NPCFightTask;
import iyunu.NewTLOL.model.task.instance.PartnerStudyTask;
import iyunu.NewTLOL.model.task.instance.PickTask;
import iyunu.NewTLOL.model.task.instance.StoneMakeTask;
import iyunu.NewTLOL.model.task.instance.YingxiongtieTask;

/**
 * 任务种类
 * 
 * @author SunHonglei
 * 
 */
public enum ETaskType {

	dialogueTask(DialogueTask.class), // 对话
	killTask(KillTask.class), // 杀怪
	collectionTask(CollectionTask.class), // 收集
	joinGuildTask(JoinGuildTask.class), // 加入门派
	npcFightTask(NPCFightTask.class), // NPC战斗
	bodyIntensifyTask(BodyIntensifyTask.class), // 部位强化
	getPartnerTask(GetPartnerTask.class), // 招募伙伴
	partnerStudyTask(PartnerStudyTask.class), // 伙伴学习技能
	stoneMakeTask(StoneMakeTask.class), // 宝石合成
	intoRaids(IntoRaidsTask.class), // 进入副本
	battleOnMapTask(BattleOnMapTask.class), // 地图上杀指定怪物
	ghostTask(GhostTask.class), // 江湖追杀令
	pickTask(PickTask.class), // 采集任务
	jionGangTask(JionGangTask.class), // 加入或创建一个帮派
	finishRaidsTask(FinishRaidsTask.class), // 完成一次多人副本
	finishGuildTask(FinishGuildTask.class), // 完成一轮门派任务
	finishGhostTask(FinishGhostTask.class), // 完成一轮江湖追杀令
	yingxiongtieTask(YingxiongtieTask.class), // 英雄帖
	;

	private Class<? extends BaseTask> object;

	ETaskType(Class<? extends BaseTask> object) {
		this.object = object;
	}

	public Class<? extends BaseTask> getObject() {
		return object;
	}

}