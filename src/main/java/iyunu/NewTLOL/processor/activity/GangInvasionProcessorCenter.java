package iyunu.NewTLOL.processor.activity;

import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.server.chat.ChatServer;
import iyunu.NewTLOL.server.gang.GangServer;
import iyunu.NewTLOL.util.log.LogManager;

/**
 * 帮派入侵活动
 * 
 * @author SunHonglei
 * 
 */
public class GangInvasionProcessorCenter extends Thread {

	private volatile boolean process = true; // 是否运行
	private static final long PERIOD_WAIT = 100; // 无任务时，周期性探测是否有新任务到达
	private long finishTime;
	private Gang gang;

	public GangInvasionProcessorCenter(Gang gang, long finishTime) {
		this.finishTime = finishTime;
		this.gang = gang;
	}

	public void run() {

		ChatServer.sendGangSystem(gang, "您的帮派开启外族入侵，请速回帮派救援！！！");

		while (process) {

			long startTime = System.currentTimeMillis(), spent = 0;

			if (startTime > finishTime) {
				break;
			}

			if (gang != null && gang.getMapGangInfo().getMonsters().isEmpty()) {
				GangServer.refreshMonster(gang, ActivityManager.INVASION_MONSTER_NUM);
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
		LogManager.info("【帮派入侵处理器】已关闭");
	}

}
