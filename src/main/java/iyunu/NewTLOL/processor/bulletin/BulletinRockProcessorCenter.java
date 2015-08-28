package iyunu.NewTLOL.processor.bulletin;

import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.message.BulletinMessage;
import iyunu.NewTLOL.model.bulletin.BulletinRockInfo;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.log.LogManager;

public class BulletinRockProcessorCenter extends Thread {

	private volatile boolean process = true; // 是否运行
	private static final long PERIOD_WAIT = 1000; // 无任务时，周期性探测是否有新任务到达

	/**
	 * 关闭进程
	 */
	public void shutdown() {
		process = false;
	}

	public void run() {

		while (process) {

			BulletinRockInfo bulletinRock = BulletinManager.instance().getNextBulletinRock();
			if (bulletinRock != null) {
				String content = StringControl.yel(bulletinRock.getContent());
//				Chat chat = new Chat(EChat.system, 0, "系统", content);
//				chat.setMapId(bulletinRock.getMapId());
//				for (Role role : ServerManager.instance().getOnlinePlayers().values()) {
//					BulletinMessage.sendRockScrnBulletin(role, content, bulletinRock.getNumber());
//					ChatMessage.sendChat(role, chat);
//				}
				BulletinMessage.sendRockScrnBulletin( content, bulletinRock.getNumber());
			}

			synchronized (this) {
				try {
					wait(PERIOD_WAIT);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		LogManager.info("【滚屏公告处理器】已关闭");
	}
}
