package iyunu.NewTLOL.processor.map;

import iyunu.NewTLOL.manager.MapManager;
import iyunu.NewTLOL.message.MapMessage;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.log.LogManager;

/**
 * 队伍变化处理器
 * 
 * @author SunHonglei
 * 
 */
public class TeamProcessorCenter extends Thread {

	private volatile boolean process = true; // 是否运行
	private static final long PERIOD_WAIT = 100; // 无任务时，周期性探测是否有新任务到达

	/**
	 * 关闭进程
	 */
	public void shutdown() {
		process = false;
	}

	public void run() {
		while (process) {

			long startTime = System.currentTimeMillis(), spent = 0;

			Role role = MapManager.instance().getTeamQueue();
			if (role != null) {
				MapMessage.refreshNearbyTeam(role);
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
		LogManager.info("【队伍变化处理器】已关闭");
	}
}
