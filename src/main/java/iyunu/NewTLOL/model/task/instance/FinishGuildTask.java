package iyunu.NewTLOL.model.task.instance;

import iyunu.NewTLOL.message.TaskMessage;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.ETaskState;

/**
 * 完成一轮门派任务
 * 
 * @author SunHonglei
 * 
 */
public class FinishGuildTask extends BaseTask {

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
		FinishGuildTask task = new FinishGuildTask();
		BaseTask.init(task, this);
		
		return task;
	}

//	@Override
//	public String encode() {
//		return this.id + "#" + this.type.ordinal() + "#" + this.state.ordinal() + "#" + this.timeOut + "#" + this.receiver;
//	}

}