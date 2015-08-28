package iyunu.NewTLOL.processor.io;

import iyunu.NewTLOL.manager.IOProcessManager;
import iyunu.NewTLOL.model.io.gang.GangIOTask;

public class GangTaskProcessor extends Thread {

	public static final long PERIOD_WAIT = 100; // 无任务时，周期性探测是否有新任务到达
	private volatile boolean process = true; // 是否运行
	private IOProcessManager ioProcess;

	/**
	 * 
	 * @param tm
	 */
	public GangTaskProcessor(IOProcessManager ioProcess) {
		this.ioProcess = ioProcess;
	}

	/**
	 * 关闭进程
	 */
	public void shutdown() {
		process = false;
	}

	/**
	 * 线程体
	 */
	public final void run() {
		while (process) {
			long startTime = 0, spent = 0;
			GangIOTask task = ioProcess.getNextGangTask();
			if (task != null) {
				startTime = System.currentTimeMillis();

				task.excute();

				spent = System.currentTimeMillis() - startTime;
			} else {
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
		}
	}
}