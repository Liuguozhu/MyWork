package iyunu.NewTLOL.model.task.instance;

import iyunu.NewTLOL.message.TaskMessage;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.ETaskState;

/**
 * NPC战斗
 * 
 * @author SunHonglei
 * 
 */
public class NPCFightTask extends BaseTask {

	private int npcId;
	private boolean finish;

	@Override
	public void finishTask() {
	}

	public void finish() {
		setState(ETaskState.finish);
		TaskMessage.sendTask(role);
	}

	@Override
	public BaseTask copy() {
		NPCFightTask task = new NPCFightTask();
		BaseTask.init(task, this);

		task.setNpcId(npcId);
		task.setFinish(finish);
		return task;
	}

	@Override
	public String encode() {

		return this.id + "#" + this.type.ordinal() + "#" + this.state.ordinal() + "#" + npcId + "#" + this.timeOut + "#" + this.receiver + "#" + awardString();
	}

	/**
	 * @return the npcId
	 */
	public int getNpcId() {
		return npcId;
	}

	/**
	 * @param npcId
	 *            the npcId to set
	 */
	public void setNpcId(int npcId) {
		this.npcId = npcId;
	}

	/**
	 * @return the finish
	 */
	public boolean isFinish() {
		return finish;
	}

	/**
	 * @param finish
	 *            the finish to set
	 */
	public void setFinish(boolean finish) {
		this.finish = finish;
	}

}