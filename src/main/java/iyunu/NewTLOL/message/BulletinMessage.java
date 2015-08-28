package iyunu.NewTLOL.message;

import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class BulletinMessage {

	/**
	 * 发送滚屏公告
	 * 
	 * @param roles
	 *            角色集合
	 * @param content
	 *            公告内容
	 * @param number
	 *            播放次数
	 */
	public static void sendRockScrnBulletin(Role role, String content, int number) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_rockScrnBulletin");
			llpMessage.write("content", content);
			llpMessage.write("number", number);

			if (role != null && role.getChannel() != null && role.isLogon()) {
				role.getChannel().write(llpMessage);

			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送滚屏公告");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 发送滚屏公告
	 * 
	 * @param roles
	 *            角色集合
	 * @param content
	 *            公告内容
	 * @param number
	 *            播放次数
	 */
	public static void sendRockScrnBulletin(String content, int number) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_rockScrnBulletin");
			llpMessage.write("content", content);
			llpMessage.write("number", number);

			for (Long roleId : ServerManager.instance().getIdChannelPair().keySet()) {
				Role role = ServerManager.instance().getOnlinePlayer(roleId);
				if (role != null && role.getChannel() != null && role.isLogon()) {
					role.getChannel().write(llpMessage);
				}
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送滚屏公告");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 发送滚屏公告
	 * 
	 * @param roles
	 *            角色集合
	 * @param content
	 *            公告内容
	 * @param number
	 *            播放次数
	 */
	// public static void sendRockScrnBulletin(String content, int number,
	// List<Long> roleIds) {
	// LlpMessage llpMessage = null;
	// try {
	// llpMessage = LlpJava.instance().getMessage("s_rockScrnBulletin");
	// llpMessage.write("content", content);
	// llpMessage.write("number", number);
	// for (Long roleId : roleIds) {
	// if (ServerManager.instance().isOnline(roleId)) {
	// Role role = ServerManager.instance().getOnlinePlayer(roleId);
	// role.getChannel().write(llpMessage);
	// }
	// }
	// } catch (Exception e) {
	// LogManager.info("异常报告：发送滚屏公告");
	// e.printStackTrace();
	// } finally {
	// if (llpMessage != null) {
	// llpMessage.destory();
	// }
	// }
	// }

	/**
	 * 发送登录公告
	 * 
	 * @param role
	 *            角色对象
	 * @param content
	 *            公告内容
	 */
	public static void sendLogonBulletion(Role role, String content) {
		LlpMessage llpMessage = null;
		try {
			llpMessage = LlpJava.instance().getMessage("s_logonBulletion");
			llpMessage.write("content", content);
			if (role != null && role.getChannel() != null && role.isLogon() && role.getLevel() > 20) {
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送登录公告");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

}
