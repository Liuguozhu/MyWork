package iyunu.NewTLOL.server.helper;

import iyunu.NewTLOL.json.HelperJson;
import iyunu.NewTLOL.message.HelperMessage;
import iyunu.NewTLOL.model.helper.EHelper;
import iyunu.NewTLOL.model.helper.instance.HelperAward;
import iyunu.NewTLOL.model.role.Role;

import java.util.List;

public class HelperServer {

	/**
	 * 小助手记录
	 * 
	 * @param role
	 *            角色对象
	 * @param type
	 *            小助手类型
	 */
	public static void helper(Role role, EHelper type) {

		int num = 1;
		if (role.getHelper().containsKey(type)) {
			num = role.getHelper().get(type) + 1;
		} 

		if (num < type.getNeed()) {
			role.getHelper().put(type, num);
		} else if (num == type.getNeed()) {
			
			role.getHelper().put(type, num);
			role.setLivenessScore(role.getLivenessScore() + type.getScore());
			List<HelperAward> helperAwards = HelperJson.instance().getHelperAwards();

			for (HelperAward helperAward : helperAwards) {
				if (role.getLivenessScore() >= helperAward.getScore() && role.getLivenessScoreMap().get(helperAward.getScore()) != 2) {
					role.getLivenessScoreMap().put(helperAward.getScore(), 1);
				}
			}

			HelperMessage.refreshHelperAward(role);
		}
		HelperMessage.refreshDailyInfo(role); // 刷新每日活动状态
	}

}
