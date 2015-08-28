package iyunu.NewTLOL.model.task.instance;

import iyunu.NewTLOL.message.TaskMessage;
import iyunu.NewTLOL.model.buffRole.EBuffType;
import iyunu.NewTLOL.model.buffRole.instance.BuffRole;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.ETaskState;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

public class KillTask extends BaseTask {

	private HashMap<Long, Integer> monsters = new HashMap<Long, Integer>(); // 怪物集合
	private HashMap<Long, Integer> finish = new HashMap<Long, Integer>(); // 已击杀怪物集合

	@Override
	public void finishTask() {
		if (state.equals(ETaskState.during)) {
			boolean reuslt = true;
			Set<Entry<Long, Integer>> set = monsters.entrySet();
			for (Iterator<Entry<Long, Integer>> it = set.iterator(); it.hasNext();) {
				Entry<Long, Integer> entry = it.next();
				Integer num = finish.get(entry.getKey());
				if (num == null || num < entry.getValue()) {
					reuslt = false;
					break;
				}
			}
			if (reuslt) {
				setState(ETaskState.finish);
			}
			// ======刷新任务信息======
			TaskMessage.sendTask(role);
		}
	}

	public void add(Long monsterId, int num) {
		if (role.getBuffs().containsKey(EBuffType.monster)) {
			BuffRole buffRole = role.getBuffs().get(EBuffType.monster);
			num = num * (buffRole.getValue() / 10000);
		}

		if (state.equals(ETaskState.during) && finish.containsKey(monsterId)) {
			finish.put(monsterId, finish.get(monsterId) + num);
		} else {
			finish.put(monsterId, num);
		}
		finishTask();
	}

	@Override
	public BaseTask copy() {
		KillTask task = new KillTask();
		BaseTask.init(task, this);

		for (Map.Entry<Long, Integer> entry : monsters.entrySet()) {
			task.getMonsters().put(entry.getKey(), entry.getValue());
			task.getFinish().put(entry.getKey(), 0);
		}
		return task;
	}

	@Override
	public String encode() {
		SerializeWriter out1 = new SerializeWriter();
		JSONSerializer serializer1 = new JSONSerializer(out1);
		serializer1.write(monsters);
		SerializeWriter out2 = new SerializeWriter();
		JSONSerializer serializer2 = new JSONSerializer(out2);
		serializer2.write(finish);
		return this.id + "#" + this.type.ordinal() + "#" + this.state.ordinal() + "#" + serializer1.toString() + "#" + serializer2.toString() + "#" + this.timeOut + "#" + this.receiver + "#" + awardString();
	}

	/**
	 * @return the monsters
	 */
	public Map<Long, Integer> getMonsters() {
		return monsters;
	}

	/**
	 * @return the finish
	 */
	public Map<Long, Integer> getFinish() {
		return finish;
	}

	/**
	 * @param monsters
	 *            the monsters to set
	 */
	public void setMonsters(HashMap<Long, Integer> monsters) {
		this.monsters = monsters;
	}

	/**
	 * @param finish
	 *            the finish to set
	 */
	public void setFinish(HashMap<Long, Integer> finish) {
		this.finish = finish;
	}

}
