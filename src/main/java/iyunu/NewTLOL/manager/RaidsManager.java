package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.Util;

public class RaidsManager {

	public static void addRaidsNumber(Role role, int index) {
		if (role.getRaidsNumber().containsKey(index)) {
			role.getRaidsNumber().put(index, role.getRaidsNumber().get(index) + 1);
		} else {
			role.getRaidsNumber().put(index, 1);
		}
	}

	public static void decRaidsNumber(Role role, int raidsId) {
		if (role.getRaidsNumber().containsKey(raidsId)) {
			role.getRaidsNumber().put(raidsId, Util.matchZero(role.getRaidsNumber().get(raidsId) - 1));
		}
	}

	/**
	 * 副本是否双倍掉落
	 * 
	 * @return
	 */
	public static boolean isDouble() {
		long now = System.currentTimeMillis();
		int hour = Time.getHour(now);
		// int min = Time.getMinute(now);
		if (hour >= 21 && hour <= 23) { // 21点到23点59
			return true;
		}
		return false;
	}

	/**
	 * 获取副本剩余次数
	 * 
	 * @param role
	 *            角色对象
	 * @param raidsId
	 *            副本索引
	 * @return 剩余次数
	 */
	public static int getRaidsNumber(Role role, int index) {
		if (role.getRaidsNumber().containsKey(index)) {
			int num = role.getRaidsNumber().get(index);
			if (num >= 3) {
				return 0;
			} else {
				return 3 - num;
			}
		} else {
			return 3;
		}
	}
}
