package iyunu.NewTLOL.server.question;

import iyunu.NewTLOL.model.role.Role;

/**
 * @function 每日问答
 * @author LuoSR
 * @date 2014年3月18日
 */
public class QuestionServer {

	/**
	 * @function 计算每日答题经验奖励
	 * @author LuoSR
	 * @param role
	 *            玩家对象
	 * @return 金币
	 * @date 2014年3月18日
	 */
	public static int countAnswerAwardExp(Role role) {
		int exp = 0;
		if (role.getLevel() <= 35) {
			exp = (role.getLevel() * 1000 + 10000) / 60;
		} else {
			exp = (role.getLevel() * 15000 - 450000) / 60;
		}
		return exp;
	}

	/**
	 * @function 计算每日答题金钱奖励
	 * @author LuoSR
	 * @param role
	 *            玩家对象
	 * @return 金币
	 * @date 2014年3月18日
	 */
	public static int countAnswerAwardGold(Role role) {
		int gold = 0;
		if (role.getLevel() <= 35) {
			gold = (role.getLevel() * 400 + 4000) / 80;
		} else {
			gold = (role.getLevel() * 9000 - 180000) / 80;
		}
		return gold;
	}

	/**
	 * @function 计算每日积分奖励
	 * @author LuoSR
	 * @param role
	 *            玩家对象
	 * @return 积分
	 * @date 2014年3月18日
	 */
	public static int countScore(Role role) {
		return role.getDailyAnswerContinuousTrueNum() * (role.getDailyAnswerContinuousTrueNum() + 1);
	}
}
