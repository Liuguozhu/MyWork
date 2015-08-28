package iyunu.NewTLOL.model.task.instance;

import iyunu.NewTLOL.message.TaskMessage;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.ETaskState;

/**
 * 对话任务
 * 
 * @author SunHonglei
 * 
 */
public class DialogueTask extends BaseTask {

	@Override
	public void finishTask() {
		if (state.equals(ETaskState.during)) {
			setState(ETaskState.finish);
			// ======刷新任务信息======
			TaskMessage.sendTask(role);

		}
	}

	@Override
	public BaseTask copy() {
		DialogueTask task = new DialogueTask();
		BaseTask.init(task, this);

		return task;
	}

//	@Override
//	public String encode() {
//		return this.id + "#" + this.type.ordinal() + "#" + this.state.ordinal() + "#" + this.timeOut + "#" + this.receiver;
//	}

}