package iyunu.NewTLOL.processor.bulletin;

import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.message.ChatMessage;
import iyunu.NewTLOL.model.chat.Chat;
import iyunu.NewTLOL.util.StringControl;
import iyunu.NewTLOL.util.log.LogManager;

public class BulletinChatProcessorCenter extends Thread {

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

			Chat chat = BulletinManager.instance().getNextBulletinChat();
			if (chat != null) {
				chat.setContent(StringControl.yel(chat.getContent()));
				ChatMessage.sendChat(chat);
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
		LogManager.info("【怪物处理器】已关闭");
	}
}
