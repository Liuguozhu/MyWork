package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.processor.LogoutProcessorCenter;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LogoutManager {

	public static ConcurrentLinkedQueue<Role> queue = new ConcurrentLinkedQueue<Role>();
	public static List<Role> roleList = new ArrayList<Role>();

	public static void addRole(Role role, String reason) {
		queue.add(role);
		// 发送下线原因协议
		SendMessage.refreshLogOutReason(role, reason);
	}

	public static List<Role> nextRole(int num) {
		roleList.clear();
		for (int i = 0; i < num; i++) {
			roleList.add(queue.poll());
		}
		return roleList;
	}

	public static int getNum() {
		return queue.size();
	}

	public LogoutProcessorCenter logoutProcessorCenter;

	/**
	 * 初始化IO处理器
	 */
	public void init() {

		logoutProcessorCenter = new LogoutProcessorCenter();
		logoutProcessorCenter.start();

		LogManager.info("【下线处理器】加载完成");
	}

	/**
	 * 停机
	 */
	public void shutdown() {

		// 如果系统中还有数据尚未存完，则等候其存储完成
		while (!queue.isEmpty()) {
			try {
				Thread.sleep(500);
			} catch (Exception interruptedException) {
				interruptedException.printStackTrace();
			}
		}
		// 关掉所有线程
		logoutProcessorCenter.shutdown();

		LogManager.info("【下线处理器】已关闭");
	}
}
