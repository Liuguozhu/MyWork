package iyunu.NewTLOL.processor;

import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.TaskMessage;
import iyunu.NewTLOL.model.activity.fbl.ActivityFblInfo;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.model.task.ETaskState;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class FblProcessorCenter extends Thread {

	private volatile boolean process = true; // 是否运行
	private static final long PERIOD_WAIT = 1000; // 无任务时，周期性探测是否有新任务到达

	/**
	 * 关闭进程
	 */
	public void close() {
		process = false;
	}

	public void run() {
		LogManager.info("【发布令处理器】已开启");
		while (process) {

			long startTime = System.currentTimeMillis(), spent = 0;

			ConcurrentHashMap<Long, ActivityFblInfo> map = ActivityManager.activityFblTimer;
			Iterator<Entry<Long, ActivityFblInfo>> it = map.entrySet().iterator();
			while (it.hasNext()) {

				Entry<Long, ActivityFblInfo> entry = it.next();

				if (entry.getValue().getTimeOut() < startTime) {

					if (entry.getValue().getState() == 1) {
						entry.getValue().setState(0);
						if (ServerManager.instance().isOnline(entry.getValue().getReceverId())) {
							Role role = ServerManager.instance().getOnlinePlayer(entry.getValue().getReceverId());
							if (role.getTasks().containsKey(entry.getValue().getTaskId())) {
								role.getTasks().get(entry.getValue().getTaskId()).setState(ETaskState.failed);
								TaskMessage.sendTask(role);
							}
						}
					}

					ActivityManager.activityFblTimer.remove(entry.getKey());
				}
			}

			spent = System.currentTimeMillis() - startTime;
			if (spent < PERIOD_WAIT) {
				synchronized (this) {
					try {
						wait(PERIOD_WAIT);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		LogManager.info("【发布令理器】已关闭");
	}

}
