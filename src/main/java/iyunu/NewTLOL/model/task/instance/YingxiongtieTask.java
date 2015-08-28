package iyunu.NewTLOL.model.task.instance;

import iyunu.NewTLOL.message.TaskMessage;
import iyunu.NewTLOL.model.buffRole.EBuffType;
import iyunu.NewTLOL.model.buffRole.instance.BuffRole;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.ETaskState;

public class YingxiongtieTask extends BaseTask {

	private int need; // 任务需求（杀怪数量）
	private int finishNum; // 已完成杀怪数量

	@Override
	public void finishTask() {
		if (state.equals(ETaskState.during)) {

			if (finishNum >= need) {
				setState(ETaskState.finish);
			}

			// ======刷新任务信息======
			TaskMessage.sendTask(role);

		}
	}

	public void add(int num) {
		if (state.equals(ETaskState.during)) {
			if (role.getBuffs().containsKey(EBuffType.monster)) {
				BuffRole buffRole = role.getBuffs().get(EBuffType.monster);
				num = num * (buffRole.getValue() / 10000);
			}

			finishNum += num;
		}
		finishTask();
	}

	@Override
	public BaseTask copy() {
		YingxiongtieTask task = new YingxiongtieTask();
		BaseTask.init(task, this);

		task.setNeed(need);

		return task;
	}

	@Override
	public String encode() {
		return this.id + "#" + this.type.ordinal() + "#" + this.state.ordinal() + "#" + this.finishNum + "#" + this.timeOut + "#" + this.receiver + "#" + awardString();
	}

	public int getNeed() {
		return need;
	}

	public void setNeed(int need) {
		this.need = need;
	}

	public int getFinishNum() {
		return finishNum;
	}

	public void setFinishNum(int finishNum) {
		this.finishNum = finishNum;
	}

}
