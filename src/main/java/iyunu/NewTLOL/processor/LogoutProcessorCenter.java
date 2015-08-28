package iyunu.NewTLOL.processor;

import iyunu.NewTLOL.manager.LogoutManager;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.List;

public class LogoutProcessorCenter extends Thread {

	private volatile boolean process = true; // 是否运行
	public static final long PERIOD_WAIT = 100; // 无任务时，周期性探测是否有新任务到达
	public static int num = 0;

	/**
	 * 关闭进程
	 */
	public void shutdown() {
		process = false;
	}

	public void run() {
		while (process) {
			long startTime = System.currentTimeMillis(), spent = 0;
			List<Role> rolelist = null;
			if (num == 0) {
				num = LogoutManager.getNum();
			} else {
				try {
					wait(PERIOD_WAIT);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				rolelist = LogoutManager.nextRole(num);
			}

			if (rolelist != null) {
				for (Role role : rolelist) {
					RoleManager.logout(role, "角色正常下线");
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
		LogManager.info("【角色下线处理器】已关闭");
	}

}
