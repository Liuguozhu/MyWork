package iyunu.NewTLOL.server.base;

import iyunu.NewTLOL.manager.MultipleManager;
import iyunu.NewTLOL.model.buffRole.EBuffType;
import iyunu.NewTLOL.model.buffRole.instance.BuffRole;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.buff.BuffServer;
import iyunu.NewTLOL.util.Time;

public class ExpServer {

	/**
	 * vip加成
	 * 
	 * @return 加成经验
	 */
	public static int additionVip(Role role, int baseExp) {
		return 0;
	}

	/**
	 * 系统加成
	 * 
	 * @return 加成经验
	 */
	public static int additionSystem(Role role, int baseExp) {

		long now = System.currentTimeMillis();
		if (MultipleManager.EXP_START_TIME <= now && MultipleManager.EXP_END_TIME >= now) {
			int hour = Time.getHour(now);
			int min = Time.getMinute(now);
			if (MultipleManager.EXP_START_H <= hour && MultipleManager.EXP_END_H >= hour && MultipleManager.EXP_START_M <= min && MultipleManager.EXP_END_M >= min) {
				return baseExp * (MultipleManager.EXP_MULTIPLE - 1);
			}
		}

		return 0;
	}

	/**
	 * BUFF加成
	 * 
	 * @return 加成经验
	 */
	public static int additionBuff(Role role, int baseExp) {

		// 检查BUFF到期
		BuffServer.checkBuff(role);
		int value = 0;
		for (BuffRole buffRole : role.getBuffs().values()) {
			if (buffRole.getType().equals(EBuffType.exp) || buffRole.getType().equals(EBuffType.doubleExp)) {
				if (buffRole.getValueType() == 0) {
					value += buffRole.getValue();
				} else {
					value += (int) (baseExp * (buffRole.getValue() / 10000f));
				}
			}
		}

		return value;
	}

	public static int additionBuff(Role role) {
		// 检查BUFF到期
		BuffServer.checkBuff(role);
		int value = 0;
		for (BuffRole buffRole : role.getBuffs().values()) {
			if (buffRole.getType().equals(EBuffType.exp) || buffRole.getType().equals(EBuffType.doubleExp)) {
				value += buffRole.getValue();
			}
		}
		return value;
	}

}
