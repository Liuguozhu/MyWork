package iyunu.NewTLOL.processor.activity;

import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.manager.QiDaERenManager;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.log.LogManager;

/**
 * 帮派入侵活动
 * 
 * @author SunHonglei
 * 
 */
public class QiDaERenProcessorCenter extends Thread {

	private volatile boolean process = true; // 是否运行
	private static final long PERIOD_WAIT = 100; // 无任务时，周期性探测是否有新任务到达
	long intervalTime = 0;

	/**
	 * 关闭进程
	 */
	public void shutdown() {
		process = false;
	}

	public void run() {
		while (process) {

			long startTime = System.currentTimeMillis(), spent = 0;
			if (startTime >= intervalTime && startTime <= ActivityManager.QIDAEREN_END_TIME) {

				QiDaERenManager.instance().refreshQiDaERen();

				intervalTime = startTime + Time.MILLISECOND * 60 * 10;
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
		LogManager.info("【七大恶人处理器】已关闭");
	}
}
