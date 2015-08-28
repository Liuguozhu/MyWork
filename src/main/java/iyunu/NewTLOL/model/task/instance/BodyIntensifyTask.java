package iyunu.NewTLOL.model.task.instance;

import iyunu.NewTLOL.message.TaskMessage;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.ETaskState;

/**
 * 部位强化任务
 * 
 * @author SunHonglei
 * 
 */
public class BodyIntensifyTask extends BaseTask {

	@Override
	public void finishTask() {
		if (state.equals(ETaskState.during)) {
			for (Integer level : role.getBodyIntensify().values()) {
				if (level > 0) {
					setState(ETaskState.finish);
					// ======刷新任务信息======
					TaskMessage.sendTask(role);

					return;
				}
			}
		}
	}

	@Override
	public BaseTask copy() {
		BodyIntensifyTask task = new BodyIntensifyTask();
		BaseTask.init(task, this);

		return task;
	}

//	@Override
//	public String encode() {
//		return this.id + "#" + this.type.ordinal() + "#" + this.state.ordinal() + "#" + this.timeOut + "#" + this.receiver;
//	}

}