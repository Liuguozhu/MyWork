package iyunu.NewTLOL.model.task.instance;

import iyunu.NewTLOL.message.TaskMessage;
import iyunu.NewTLOL.model.task.BaseTask;
import iyunu.NewTLOL.model.task.ETaskState;
import iyunu.NewTLOL.util.Translate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

public class PickTask extends BaseTask {

	private Map<Integer, Integer> needs = new HashMap<Integer, Integer>(); // 需要任务物品<物品编号，数量>
	private Map<Integer, Integer> finish = new HashMap<Integer, Integer>(); // 物品集合

	@Override
	public void finishTask() {
		if (state.equals(ETaskState.during)) {

			boolean result = true;
			Set<Entry<Integer, Integer>> set = needs.entrySet();
			for (Iterator<Entry<Integer, Integer>> it = set.iterator(); it.hasNext();) {
				Entry<Integer, Integer> entry = it.next();
				Integer num = finish.get(entry.getKey());

				if (num == null || Translate.integerToInt(num) < Translate.integerToInt(entry.getValue())) {
					result = false;
					break;
				}
			}

			if (result) {
				setState(ETaskState.finish);
			}

			// ======刷新任务信息======
			TaskMessage.sendTask(role);
		}
	}

	public void add(Integer itemId, int num) {
		if (state.equals(ETaskState.during) && needs.containsKey(itemId)) {
			if (finish.containsKey(itemId)) {
				finish.put(itemId, finish.get(itemId) + num);
			} else {
				finish.put(itemId, num);
			}
			finishTask();
		}
	}

	@Override
	public BaseTask copy() {
		PickTask task = new PickTask();
		BaseTask.init(task, this);

		for (Map.Entry<Integer, Integer> entry : needs.entrySet()) {
			task.getNeeds().put(entry.getKey(), entry.getValue());
			task.getFinish().put(entry.getKey(), 0);
		}

		return task;
	}

	@Override
	public String encode() {
		SerializeWriter out1 = new SerializeWriter();
		JSONSerializer serializer1 = new JSONSerializer(out1);
		serializer1.write(needs);
		SerializeWriter out2 = new SerializeWriter();
		JSONSerializer serializer2 = new JSONSerializer(out2);
		serializer2.write(finish);
		return this.id + "#" + this.type.ordinal() + "#" + this.state.ordinal() + "#" + serializer1.toString() + "#" + serializer2.toString() + "#" + this.timeOut + "#" + this.receiver + "#" + awardString();
	}

	/**
	 * @return the needs
	 */
	public Map<Integer, Integer> getNeeds() {
		return needs;
	}

	/**
	 * @return the finish
	 */
	public Map<Integer, Integer> getFinish() {
		return finish;
	}

	/**
	 * @param needs
	 *            the needs to set
	 */
	public void setNeeds(Map<Integer, Integer> needs) {
		this.needs = needs;
	}

	/**
	 * @param finish
	 *            the finish to set
	 */
	public void setFinish(Map<Integer, Integer> finish) {
		this.finish = finish;
	}

}
