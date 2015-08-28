package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.message.BulletinMessage;
import iyunu.NewTLOL.model.bulletin.BulletinChat;
import iyunu.NewTLOL.model.bulletin.BulletinRock;
import iyunu.NewTLOL.model.bulletin.BulletinRockInfo;
import iyunu.NewTLOL.model.chat.Chat;
import iyunu.NewTLOL.model.chat.EChat;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.processor.bulletin.BulletinChatProcessorCenter;
import iyunu.NewTLOL.processor.bulletin.BulletinRockProcessorCenter;
import iyunu.NewTLOL.service.iface.bulletin.BulletinService;
import iyunu.NewTLOL.util.Time;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class BulletinManager {

	/**
	 * 私有构造方法
	 */
	private BulletinManager() {

	}

	/**
	 * 返回BulletinManager实例
	 * 
	 * @return BulletinManager实例
	 */
	public static BulletinManager instance() {
		return instance;
	}

	private ConcurrentLinkedQueue<BulletinRockInfo> bulletinRockQueue = new ConcurrentLinkedQueue<BulletinRockInfo>();
	private ConcurrentLinkedQueue<Chat> bulletinChatQueue = new ConcurrentLinkedQueue<Chat>();
	private static BulletinManager instance = new BulletinManager();
	private List<BulletinChat> chats = new ArrayList<BulletinChat>();
	private List<BulletinRock> rocks = new ArrayList<BulletinRock>();
	private String systemBulletin = ServerManager.instance().getName();
	private String logonBulletin = "";
	private ScheduledThreadPoolExecutor chatBulletinExec;
	private ScheduledThreadPoolExecutor rockBulletinExec;

	private BulletinChatProcessorCenter bulletinChatProcessorCenter;
	private BulletinRockProcessorCenter bulletinRockProcessorCenter;

	public void clear() {
		chats.clear();
		rocks.clear();
		systemBulletin = "";
		logonBulletin = "";
		chatBulletinExec = null;
		rockBulletinExec = null;
	}

	public void init() {
		stopChatBulletin();
		stopRockBulletin();
		clear();

		BulletinService bulletinService = Spring.instance().getBean("bulletinService", BulletinService.class);
		systemBulletin = bulletinService.querySys();
		logonBulletin = bulletinService.queryLogon();

		for (BulletinChat bulletin : bulletinService.queryChat()) {
			bulletin.init();
			Chat chat = new Chat(EChat.system, 0, "公告", bulletin.getContent());
			bulletin.setChat(chat);
			chats.add(bulletin);
		}

		for (BulletinRock bulletinRock : bulletinService.queryRock()) {
			bulletinRock.init();
			rocks.add(bulletinRock);
		}
		

		startChatBulletin();
		startRockBulletin();

	}

	public void initLogonBulletin() {
		BulletinService bulletinService = Spring.instance().getBean("bulletinService", BulletinService.class);
		logonBulletin = bulletinService.queryLogon();
	}

	public void initBulletion() {
		bulletinChatProcessorCenter = new BulletinChatProcessorCenter();
		bulletinChatProcessorCenter.start();
		bulletinRockProcessorCenter = new BulletinRockProcessorCenter();
		bulletinRockProcessorCenter.start();
	}

	public void stop() {
		if (bulletinChatProcessorCenter != null) {
			bulletinChatProcessorCenter.shutdown();
		}
		if (bulletinRockProcessorCenter != null) {
			bulletinRockProcessorCenter.shutdown();
		}

		stopChatBulletin();
		stopRockBulletin();
	}

	/**
	 * 启动聊天公告
	 */
	public void startChatBulletin() {

		chatBulletinExec = new ScheduledThreadPoolExecutor(chats.size());
		for (BulletinChat systemBulletin : chats) {
			final BulletinChat bulletin = systemBulletin;

			long start = (bulletin.getStartTime() - System.currentTimeMillis()) / Time.MILLISECOND;
			Runnable runnable = new Runnable() {

				@Override
				public void run() {
					if (bulletin.getEndTime() > System.currentTimeMillis()) {
						BulletinManager.instance().addBulletinChat(bulletin.getChat().getContent());
					}
				}
			};

			chatBulletinExec.scheduleAtFixedRate(runnable, start, bulletin.getPeriod(), TimeUnit.SECONDS);

		}
	}

	/**
	 * 停止系统公告
	 */
	public void stopChatBulletin() {
		if (chatBulletinExec != null) {
			chatBulletinExec.shutdown();
		}
	}

	/**
	 * 启动滚动公告
	 */
	public void startRockBulletin() {
		rockBulletinExec = new ScheduledThreadPoolExecutor(rocks.size());
		for (BulletinRock rockBulletin : rocks) {
			final BulletinRock bulletin = rockBulletin;
			long start = (bulletin.getStartTime() - System.currentTimeMillis()) / Time.MILLISECOND;

			Runnable runnable = new Runnable() {

				@Override
				public void run() {
					if (bulletin.getEndTime() > System.currentTimeMillis()) {
						BulletinManager.instance().addBulletinRock(bulletin.getContent(), bulletin.getNumber());
						// BulletinMessage.sendRockScrnBulletin(bulletin.getContent(),
						// bulletin.getNumber());
					}
				}
			};

			rockBulletinExec.scheduleAtFixedRate(runnable, start, bulletin.getPeriod(), TimeUnit.SECONDS);
		}
	}

	/**
	 * 停止滚动公告
	 */
	public void stopRockBulletin() {
		if (rockBulletinExec != null) {
			rockBulletinExec.shutdown();
		}
	}

	/**
	 * @return the systemBulletin
	 */
	public String getSystemBulletin() {
		return systemBulletin;
	}

	/**
	 * 添加滚屏公告
	 * 
	 * @param bulletinRock
	 *            滚屏公告
	 */
	public void addBulletinRock(String content, int num) {
		bulletinRockQueue.add(new BulletinRockInfo(content, num));
		// LogManager.exception("BulletinManager*********bulletinRockQueue********"
		// + bulletinRockQueue.size());
	}

	/**
	 * 添加滚屏公告
	 * 
	 * @param bulletinRock
	 *            滚屏公告
	 */
	public void addBulletinRock(String content, int num, int mapId) {
		bulletinRockQueue.add(new BulletinRockInfo(content, num, mapId));
		// LogManager.exception("BulletinManager*********bulletinRockQueue********"
		// + bulletinRockQueue.size());
	}

	/**
	 * 添加聊天公告
	 * 
	 * @param chat
	 *            聊天公告
	 */
	public void addBulletinChat(String content) {
		bulletinChatQueue.add(new Chat(EChat.system, 0, "系统", content));
		// LogManager.exception("BulletinManager*********bulletinChatQueue********"
		// + bulletinChatQueue.size());
	}

	/**
	 * 获取下一个滚屏公告
	 * 
	 * @return 滚屏公告
	 */
	public BulletinRockInfo getNextBulletinRock() {
		return bulletinRockQueue.poll();
	}

	/**
	 * 获取下一个聊天公告
	 * 
	 * @return 聊天公告
	 */
	public Chat getNextBulletinChat() {
		return bulletinChatQueue.poll();
	}

	/**
	 * 发送登录公告
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void sendLogonBulletin(Role role) {
		if (BulletinManager.instance().logonBulletin != null && !"".equals(BulletinManager.instance().logonBulletin)) {
			BulletinMessage.sendLogonBulletion(role, BulletinManager.instance().logonBulletin);
		}
	}

	/**
	 * @return the logonBulletin
	 */
	public String getLogonBulletin() {
		return logonBulletin;
	}

}
