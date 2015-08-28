package iyunu.NewTLOL.processor.activity;

import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.manager.HelperManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.HelperMessage;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.log.LogManager;

public class AnswerProcessorCenter extends Thread {

	private volatile boolean process = true; // 是否运行
	private static final long PERIOD_WAIT = 100; // 无任务时，周期性探测是否有新任务到达

	/**
	 * 关闭进程
	 */
	public void shutdown() {
		process = false;
	}

	public void run() {

		ActivityManager.ANSWER_STATE = true;
		long timeOut = System.currentTimeMillis() + Time.MINUTE_MILLISECOND * 30;
		HelperManager.DAILYINFO_ANSWER.setState(0);
		SendMessage.answerInformForAll();

		// 刷新每日活动状态

		while (process) {
			long startTime = System.currentTimeMillis(), spent = 0;

			if (startTime >= timeOut) {
				ActivityManager.ANSWER_STATE = false;
				HelperManager.DAILYINFO_ANSWER.setState(1);
				for (Long roleId : ServerManager.instance().getIdChannelPair().keySet()) {
					Role role = ServerManager.instance().getOnlinePlayer(roleId);
					if (role != null && role.getChannel() != null && role.getLevel() >= 15) {
						HelperMessage.refreshDailyInfo(role); // 刷新每日活动状态
					}
				}

				process = false;
				break;
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
		LogManager.info("【每日答题处理器】已关闭");
	}

}
