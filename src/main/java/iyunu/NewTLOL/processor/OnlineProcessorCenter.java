package iyunu.NewTLOL.processor;

import iyunu.NewTLOL.manager.IOProcessManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.io.role.ERoleIOTask;
import iyunu.NewTLOL.model.io.role.instance.UpdateTask;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.log.LogManager;

public class OnlineProcessorCenter extends Thread {

	private volatile boolean process = true; // 是否运行
	public static final long PERIOD_WAIT = 100; // 无任务时，周期性探测是否有新任务到达

	/**
	 * 关闭进程
	 */
	public void shutdown() {
		process = false;
	}

	public void run() {
		while (process) {
			long startTime = System.currentTimeMillis(), spent = 0;

			roleHandler();

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
		LogManager.info("【在线角色处理器】已关闭");
	}

	/**
	 * 角色处理器
	 */
	public void roleHandler() {
		// System.out.println("角色处理器");
		for (Long roleId : ServerManager.instance().getIdChannelPair().keySet()) {
			Role role = ServerManager.instance().getOnlinePlayer(roleId);
			if (role == null) {
				continue;
			}
			if (role.isAround()) {
				role.getMapInfo().getMapAgent().flushNearby(); // 刷新周围玩家
			}
			updateDB(role); // 更新数据

		}
	}

	/**
	 * 更新角色数据
	 * 
	 * @param role
	 *            角色对象
	 */
	public void updateDB(Role role) {
		if (role.getUpdateTime() < System.currentTimeMillis()) {
			UpdateTask ioTask = new UpdateTask(ERoleIOTask.update, role);
			IOProcessManager.instance().addTask(ioTask);
		}
	}

}
