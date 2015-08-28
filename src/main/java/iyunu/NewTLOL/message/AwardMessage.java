package iyunu.NewTLOL.message;

import iyunu.NewTLOL.enumeration.EAward;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class AwardMessage {

	/**
	 * 获得奖励
	 * 
	 * @param role
	 *            角色对象
	 * @param award
	 *            奖励
	 */
	public static void sendAward(Role role, String award, EAward type) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_award");
				llpMessage.write("award", award);
				llpMessage.write("type", type.ordinal());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送获得奖励信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 刷新手充领奖状态
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @date 2014年4月25日
	 */
	public static void sendPayFristState(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshPayFristState");
				llpMessage.write("payFristState", role.getVip().payState());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送手充领奖状态");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 获得奖励
	 * 
	 * @param roleId
	 *            角色编号
	 * @param award
	 *            奖励
	 */
	public static void sendAward(long roleId, String award, EAward type) {
		LlpMessage llpMessage = null;
		try {
			if (ServerManager.instance().isOnline(roleId)) {
				Role role = ServerManager.instance().getOnlinePlayer(roleId);

				if (role != null && role.getChannel() != null && role.isLogon()) {
					llpMessage = LlpJava.instance().getMessage("s_award");
					llpMessage.write("award", award);
					llpMessage.write("type", type.ordinal());
					role.getChannel().write(llpMessage);
				}
			}

		} catch (Exception e) {
			LogManager.info("异常报告：发送获得奖励信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新VIP奖励
	 * 
	 * @param roleId
	 *            角色编号
	 * @param award
	 *            奖励
	 */
	public static void sendVipGift(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshVipGift");
				llpMessage.write("flag", role.getVipGift());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：发送VIP奖励信息");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}
