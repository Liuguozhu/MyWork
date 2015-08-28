package iyunu.NewTLOL.message;

import iyunu.NewTLOL.json.ActivityJson;
import iyunu.NewTLOL.json.ActivityPayJson;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 运营活动
 * 
 * @author SunHonglei
 * 
 */
public class ActivityMessage {

	/**
	 * 刷新新服活动状态
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshActivityNew(Role role) {

		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshActivityNew");
				long now = System.currentTimeMillis();
				if (ActivityPayJson.START < now && now < ActivityPayJson.END) {
					llpMessage.write("state", 1);
				} else {
					llpMessage.write("state", 0);
				}
				role.getChannel().write(llpMessage);
			}

		} catch (Exception e) {
			LogManager.info("异常报告：刷新新服活动状态");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新每日优惠活动状态
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshActivityPay(Role role) {

		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshActivityPay");
				long now = System.currentTimeMillis();
				if (ActivityJson.PAY_EVERYDAY != null && ActivityJson.PAY_EVERYDAY.getStartTime() < now && now < ActivityJson.PAY_EVERYDAY.getEndTime()) {
					llpMessage.write("state", 1);
				} else {
					llpMessage.write("state", 0);
				}
				role.getChannel().write(llpMessage);
			}

		} catch (Exception e) {
			LogManager.info("异常报告：刷新每日优惠活动状态");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * 刷新发布令剩余次数
	 * 
	 * @param role
	 *            角色对象
	 */
	public static void refreshTaskFblNum(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("refreshTaskFblNum");
				llpMessage.write("releaseNum", role.getTaskFblNum());
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新发布令剩余次数");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}
