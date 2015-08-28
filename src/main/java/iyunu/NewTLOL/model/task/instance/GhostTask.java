package iyunu.NewTLOL.model.task.instance;

import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.message.TaskMessage;
import iyunu.NewTLOL.model.map.BaseMap;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.ETaskState;

/**
 * 地图上杀指定怪物
 * 
 * @author SunHonglei
 * 
 */
public class GhostTask extends BaseTask {

	private long uid;
	private int monsterGroup;

	@Override
	public void finishTask() {
		if (state.equals(ETaskState.during)) {
			BaseMap baseMap = MapManager.instance().getMapById(arriveMap);
			if (!baseMap.isMonsterExist(uid)) {
				setState(ETaskState.failed);
				// ======刷新任务信息======
				TaskMessage.sendTask(role);
			}
		}
	}

	@Override
	public BaseTask copy() {
		GhostTask task = new GhostTask();
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