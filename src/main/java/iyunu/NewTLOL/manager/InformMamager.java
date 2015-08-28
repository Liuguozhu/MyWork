package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.role.Role;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @function 通知
 * @author LuoSR
 * @date 2014年5月23日
 */
public class InformMamager {
	/**
	 * 私有构造方法
	 */
	private InformMamager() {
	}

	private static InformMamager instance = new InformMamager();

	/**
	 * 获取InformMamager对象
	 * 
	 * @return InformMamager对象
	 */
	public static InformMamager instance() {
		return instance;
	}

	/**
	 * @function 检查首冲礼包通知
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @date 2014年5月23日
	 */
	public void checkPayFirst(Role role) {
		int state = 1;
		if (role.getVip().payState() == 1) {
			state = 0;
		}
		SendMessage.payInform(role, state);
	}

	/**
	 * @function 检查每日答题
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @date 2014年9月10日
	 */
	public void checkAnswer(Role role) {
		if (ActivityManager.ANSWER_STATE && role.getDailyAnswerState() != 1) {
			SendMessage.answerInformForOne(role);
		}
	}

	/**
	 * @function 检查在线奖励通知
	 * @author LuoSR
	 * @param role
	 *            角色对象
	 * @date 2014年5月23日
	 */
	public void checkOnlineAward(Role role) {
		int state = 1;
		Map<Integer, Integer> map = role.getOnlineAwardStateMap();
		Set<Entry<Integer, Integer>> set = map.entrySet();
		for (Iterator<Entry<Integer, Integer>> it = set.iterator(); it.hasNext();) {
			Entry<Integer, Integer> itemEntry = it.next();
			int onlineAwardState = itemEntry.getValue();
			if (onlineAwardState == 1) {
				state = 0;
				break;
			}
		}

		SendMessage.onlineAwardInform(role, state);
	}
}
