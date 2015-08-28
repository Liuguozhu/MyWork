package iyunu.NewTLOL.net.protocol.task;

import iyunu.NewTLOL.common.RoleForm;
import iyunu.NewTLOL.enumeration.common.EExp;
import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.manager.TaskManager;
import iyunu.NewTLOL.message.TaskMessage;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.server.task.TaskServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 一键完成10次任务
 * 
 * @author SunHonglei
 * 
 */
public class TaskFinishQuickly extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "完成任务成功";

		int id = msg.readInt("id"); // 1.江湖追杀令，2.环任务

		if (id == 1) { // =================================江湖追杀令==================================================
			if (online.getGhostTaskSum() >= TaskManager.GHOST_TASK_MAX) {
				result = 1;
				reason = "今天" + TaskManager.GHOST_TASK_MAX + "个江湖追杀令您已经全部完成，表现非常好！明天再接再厉！";
				return;
			}

			if (online.getEnergy() < 2) {
				result = 1;
				reason = "活力值不足2点！";
				return;
			}

			// TODO 扣除活力值
			RoleServer.energyCost(online, 2);

			if (online.getGhostTask() != null) {
				BaseTask baseTask = online.getGhostTask();
				online.setGhostTask(null); // 删除江湖追杀令任务
				online.getTasks().remove(baseTask.getId()); // 从任务列表中删除
			}

			int num = 10;
			num = (online.getGhostTaskSum() + 10) > TaskManager.GHOST_TASK_MAX ? (TaskManager.GHOST_TASK_MAX - online.getGhostTaskSum()) : num;
			TaskServer.addGhostTaskSum(online, num); // 增加任务完成总次数

			int gold = RoleForm.ghostTaskGold(online.getLevel()) * num;
			int exp = RoleForm.ghostTaskExp(online.getLevel()) * num;

			RoleServer.addGold(online, gold, EGold.taskGhostFinishQuickly);
			RoleServer.addExp(online, exp, EExp.taskGhostFinishQuickly);

		} else if (id == 2) { // ================================环任务==================================================
			if (online.getTaskCycleNum() == 0 && online.getTaskCycleRow() >= TaskManager.CYCLE_TASK_MAX) {
				result = 1;
				reason = "每周只能接取" + TaskManager.CYCLE_TASK_MAX + "轮环任务";
				return;
			}

			if (online.getEnergy() < 2) {
				result = 1;
				reason = "活力值不足2点！";
				return;
			}

			if (!RoleServer.costGold(online, online.getLevel() * 1500, EGold.taskGhostFinishQuickly)) { // 消耗绑银
				result = 1;
				reason = "绑银不足";
				return;
			}

			// TODO 扣除活力值
			RoleServer.energyCost(online, 2);

			if (online.getTaskCycleNum() == 0) { // 如果是接第一环，则轮数增加1轮
				online.setTaskCycleRow(online.getTaskCycleRow() + 1);
			}

			int num = 10;
			if (online.getTaskCycle() != null) {
				online.getTasks().remove(online.getTaskCycle().getId());
				online.setTaskCycle(null);
				num = 9;
			}

			if ((online.getTaskCycleNum() + 10) > TaskManager.CYCLE_TASK_NUM) {
				num = TaskManager.CYCLE_TASK_NUM - online.getTaskCycleNum();
				online.setTaskCycleNum(0);
			} else {
				online.setTaskCycleNum(online.getTaskCycleNum() + num);
				TaskServer.getTaskCycle(online);
			}

			int exp = RoleForm.taskCycleExp(online.getLevel()) * num;
			RoleServer.addExp(online, exp, EExp.taskCycleFinishQuickly);
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage llpMessage = null;

		try {
			llpMessage = LlpJava.instance().getMessage("s_taskFinishQuickly");
			llpMessage.write("result", result);
			llpMessage.write("reason", reason);
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
