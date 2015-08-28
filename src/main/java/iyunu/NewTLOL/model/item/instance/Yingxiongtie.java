package iyunu.NewTLOL.model.item.instance;

import iyunu.NewTLOL.json.TaskJson;
import iyunu.NewTLOL.message.TaskMessage;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.battle.BattleCharacter;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.model.helper.EHelper;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.task.ETaskState;
import iyunu.NewTLOL.model.task.instance.YingxiongtieTask;
import iyunu.NewTLOL.server.helper.HelperServer;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.Map;

/**
 * @function 英雄帖
 * @author LuoSR
 * @date 2014年10月11日
 */
public class Yingxiongtie extends Item {

	@Override
	public Yingxiongtie copy() {
		Yingxiongtie item = new Yingxiongtie();
		Item.init(item, this);

		return item;
	}

	@Override
	public void change() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean use(Role role, int num, Map<Integer, Cell> cellsMap) {
		YingxiongtieTask task = (YingxiongtieTask) TaskJson.instance().getTask(value);
		task.setRole(role);
		task.setState(ETaskState.during);

		role.getTasks().put(task.getId(), task);
		role.setYingxiongtie(task);
		TaskMessage.sendTask(role);
		LogManager.taskLog(role, task.getId(), 0); // 游戏日志
		HelperServer.helper(role, EHelper.yingxiong); // 小助手记录
		return true;
	}

	@Override
	public boolean use(BattleCharacter self, Integer foeIndex, BattleInfo battleInfo) {
		return false;
	}

}
