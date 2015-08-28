package iyunu.NewTLOL.model.task.instance;

import iyunu.NewTLOL.enumeration.Vocation;
import iyunu.NewTLOL.message.TaskMessage;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.ETaskState;

/**
 * 加入帮派
 * 
 * @author SunHonglei
 * 
 */
public class JoinGuildTask extends BaseTask {

	@Override
	public void finishTask() {
		if (state.equals(ETaskState.during) && !role.getVocation().equals(Vocation.none)) {
			setState(ETaskState.finish);
			// ======刷新任务信息======
			TaskMessage.sendTask(role);
			
		}
	}

	@Override
	public BaseTask copy() {
		JoinGuildTask task = new JoinGuildTask();
		BaseTask.init(task, this);

		return task;
	}

//	@Override
//	public String encode() {
//		return this.id + "#" + this.type.ordinal() + "#" + this.state.ordinal() + "#" + this.timeOut + "#" + this.receiver;
//	}

}