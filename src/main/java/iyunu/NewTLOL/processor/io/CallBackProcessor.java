package iyunu.NewTLOL.processor.io;

import iyunu.NewTLOL.manager.IOProcessManager;
import iyunu.NewTLOL.model.io.role.RoleIOTask;

public class CallBackProcessor extends Thread {

	public static final long PERIOD_WAIT = 100; // 无任务时，周期性探测是否有新任务到达
	private volatile boolean process = true; // 是否运行
	private IOProcessManager ioProcess;

	/**
	 * 
	 * @param tm
	 */
	public CallBackProcessor(IOProcessManager ioProcess) {
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
			RoleIOTask task = ioProcess.getNextCallBackTask();
			if (task != null) {
				startTime = System.currentTimeMillis();

				task.callBack();

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