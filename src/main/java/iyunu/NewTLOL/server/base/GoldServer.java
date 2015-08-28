package iyunu.NewTLOL.server.base;

import iyunu.NewTLOL.manager.MultipleManager;
import iyunu.NewTLOL.model.buffRole.EBuffType;
import iyunu.NewTLOL.model.buffRole.instance.BuffRole;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.buff.BuffServer;
import iyunu.NewTLOL.util.Time;

public class GoldServer {

	/**
	 * vip加成
	 * 
	 * @return 加成银两
	 */
	public static int additionVip(Role role, int baseGold) {
		return 0;
	}

	/**
	 * 系统加成
	 * 
	 * @return 加成银两
	 */
	public static int additionSystem(Role role, int baseGold) {

		long now = System.currentTimeMillis();
		if (MultipleManager.GOLD_START_TIME <= now && MultipleManager.GOLD_END_TIME >= now) {
			int hour = Time.getHour(now);
			int min = Time.getMinute(now);
			if (MultipleManager.GOLD_START_H <= hour && MultipleManager.GOLD_END_H >= hour && MultipleManager.GOLD_START_M <= min && MultipleManager.GOLD_END_M >= min) {
				return baseGold * MultipleManager.GOLD_MULTIPLE;
			}
		}

		return 0;
	}

	/**
	 * BUFF加成
	 * 
	 * @return 加成银两
	 */
	public static int additionBuff(Role role, int baseGold) {

		// 检查BUFF到期
		BuffServer.checkBuff(role);
		int value = 0;
		for (BuffRole buffRole : role.getBuffs().values()) {
			if (buffRole.getType().equals(EBuffType.gold) || buffRole.getType().equals(EBuffType.doubleGold)) {
				if (buffRole.getValueType() == 0) {
					value += buffRole.getValue();
				} else {
					value += (int) (baseGold * (buffRole.getValue() / 10000f));
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
			if (buffRole.getType().equals(EBuffType.gold) || buffRole.getType().equals(EBuffType.doubleGold)) {
				value += buffRole.getValue();
			}
		}
		return value;
	}
}
