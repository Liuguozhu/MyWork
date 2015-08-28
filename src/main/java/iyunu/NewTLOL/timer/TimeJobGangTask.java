package iyunu.NewTLOL.timer;

import iyunu.NewTLOL.manager.gang.GangTaskManager;

public class TimeJobGangTask {

	public void refreshGangTask() {
		// 刷新所有人的帮派任务
		System.out.println("执行刷新帮派~~~~~~~~~~~~~~~~~~~~~~~~~~");
		GangTaskManager.instance().initRoleGangTaskAll();
	
	}
}
