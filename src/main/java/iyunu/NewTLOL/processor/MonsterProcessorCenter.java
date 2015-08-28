package iyunu.NewTLOL.processor;

import iyunu.NewTLOL.manager.MonsterManager;
import iyunu.NewTLOL.util.log.LogManager;

public class MonsterProcessorCenter extends Thread {

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
			MonsterManager.instance().check();
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
		LogManager.info("【怪物处理器】已关闭");
	}
}
