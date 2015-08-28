package iyunu.NewTLOL.model.task.instance;

import iyunu.NewTLOL.message.TaskMessage;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.ETaskState;

/**
 * 地图上杀指定怪物
 * 
 * @author SunHonglei
 * 
 */
public class BattleOnMapTask extends BaseTask {

	private long uid;
	private int monsterGroup;

	@Override
	public void finishTask() {
		if (state.equals(ETaskState.during)) {
			if (!role.getMapInfo().getMonsterOnMaps().containsKey(uid)) {
				setState(ETaskState.finish);
				// ======刷新任务信息======
				TaskMessage.sendTask(role);

			}
		}
	}

	@Override
	public BaseTask copy() {
		BattleOnMapTask task = new BattleOnMapTask();
		BaseTask.init(task, this);

		task.setUid(uid);
		task.setMonsterGroup(monsterGroup);
		return task;
	}

	@Override
	public String encode() {
		return this.id + "#" + this.type.ordinal() + "#" + this.state.ordinal() + "#" + this.uid + "#" + this.monsterGroup + "#" + this.timeOut + "#" + this.receiver + "#" + awardString();
	}

	/**
	 * @return the uid
	 */
	public long getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(long uid) {
		this.uid = uid;
	}

	/**
	 * @return the monsterGroup
	 */
	public int getMonsterGroup() {
		return monsterGroup;
	}

	/**
	 * @param monsterGroup
	 *            the monsterGroup to set
	 */
	public void setMonsterGroup(int monsterGroup) {
		this.monsterGroup = monsterGroup;
	}

}