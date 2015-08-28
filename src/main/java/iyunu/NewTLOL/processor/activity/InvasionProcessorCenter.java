package iyunu.NewTLOL.processor.activity;

import iyunu.NewTLOL.manager.ActivityManager;
import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.server.chat.ChatServer;
import iyunu.NewTLOL.server.gang.GangServer;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.log.LogManager;

/**
 * 帮派入侵活动
 * 
 * @author SunHonglei
 * 
 */
public class InvasionProcessorCenter extends Thread {

	private volatile boolean process = true; // 是否运行
	private static final long PERIOD_WAIT = 100; // 无任务时，周期性探测是否有新任务到达

	/**
	 * 关闭进程
	 */
	public void shutdown() {
		process = false;

		for (Gang gang : GangManager.instance().getMap().values()) {
			GangServer.removeMonster(gang);
		}
	}

	public void run() {
		ActivityManager.INVASION_STATE = true;
		long timeOut = System.currentTimeMillis() + Time.MINUTE_MILLISECOND * 30;

		while (process) {

			long startTime = System.currentTimeMillis(), spent = 0;

			if (startTime >= timeOut) {
				ActivityManager.INVASION_STATE = false;
				
				shutdown();
				
				String content = StringControl.yel("帮派入侵活动已结束！");
				BulletinManager.instance().addBulletinRock(content, 2);
				
				break;
			}

			for (Gang gang : GangManager.instance().getMap().values()) {
				if (gang != null && gang.getMapGangInfo().getMonsters().isEmpty() && gang.getLevel() >= 2) {
					GangServer.refreshMonster(gang, ActivityManager.INVASION_MONSTER_NUM);
					ChatServer.sendGangSystem(gang, "您的帮派被外族入侵，请速回帮派救援！！！");
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
		LogManager.info("【帮派入侵处理器】已关闭");
	}
}
