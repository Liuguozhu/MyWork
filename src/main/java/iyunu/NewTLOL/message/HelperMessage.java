package iyunu.NewTLOL.message;

import iyunu.NewTLOL.json.HelperJson;
import iyunu.NewTLOL.manager.HelperManager;
import iyunu.NewTLOL.model.helper.res.DailyInfo;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 日常活动
 * 
 * @author SunHonglei
 * 
 */
public class HelperMessage {

	public static void refreshDailyInfo(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshDailyInfo");

				for (DailyInfo dailyInfoRes : HelperJson.instance().getDailyInfos()) {
					LlpMessage message = llpMessage.write("dailyInfoList");
					message.write("index", dailyInfoRes.getIndex());
					int state =  HelperManager.state(role, dailyInfoRes);
					message.write("state",state);
				}
				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新每日活动");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}

	/**
	 * @function 刷新活跃度礼包
	 * @author LuoSR
	 * @param role
	 *            角色编号
	 * @date 2014年5月16日
	 */
	public static void refreshHelperAward(Role role) {
		LlpMessage llpMessage = null;
		try {
			if (role != null && role.getChannel() != null && role.isLogon()) {
				llpMessage = LlpJava.instance().getMessage("s_refreshHelperAward");
				llpMessage.write("activityValue", role.getLivenessScore());
				Map<Integer, Integer> livenessScoreMap = role.getLivenessScoreMap();

				Set<Entry<Integer, Integer>> itemSet = livenessScoreMap.entrySet();
				for (Iterator<Entry<Integer, Integer>> itemIt = itemSet.iterator(); itemIt.hasNext();) {
					LlpMessage helperAwardInfo = llpMessage.write("helperAwardInfoList");
					Entry<Integer, Integer> itemEntry = itemIt.next();
					int score = itemEntry.getKey();
					int state = itemEntry.getValue();
					helperAwardInfo.write("score", score);
					helperAwardInfo.write("state", state);
				}

				role.getChannel().write(llpMessage);
			}
		} catch (Exception e) {
			LogManager.info("异常报告：刷新活跃度礼包");
			e.printStackTrace();
		} finally {
			if (llpMessage != null) {
				llpMessage.destory();
			}
		}
	}
}
