package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.model.helper.res.DailyInfo;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.net.protocol.activity.HuntTreasure;

public class HelperManager {

	public static DailyInfo DAILYINFO_ANSWER;
	public static DailyInfo DAILYINFO_XUEZHAN;

	public static int state(Role role, DailyInfo dailyInfo) {

		switch (dailyInfo.getType()) {
		case answer:
			if (role.getLevel() >= 20) {
				return dailyInfo.getState();
			}
			return 1;
		case guild:
			if (role.getLevel() >= 30 && role.getGuildTaskSum() < TaskManager.GUILD_TASK_MAX) {
				return 0;
			}
			return 1;
		case ghost:
			if (role.getLevel() >= 30 && role.getGhostTaskSum() < TaskManager.GHOST_TASK_MAX) {
				return 0;
			}
			return 1;
		case xuezhan:
			if (role.getLevel() >= 30) {
				return dailyInfo.getState();
			}
			return 1;
		case yingxiong:
			if (role.getLevel() >= 20 && role.getYxtTaskNum() < (role.getVip().getLevel().getYingxiongtieAdd() + 5)) {
				return 0;
			}
			return 1;
		case xunbao:
			if (role.getLevel() >= 30 && role.getCoin() >= HuntTreasure.DAN_SHOU_MO_JIN) {
				return 0;
			}
			return 1;
		case fbl:
			if (role.getLevel() >= 30 && role.getTaskFblNum() > 0) {
				return 0;
			}
			return 1;
		case raids:
			if (role.getLevel() >= 20 && role.getTaskFblNum() > 0) {
				return 0;
			}
			return 1;
		case taskCycle:
			if (role.getLevel() >= 30 && role.getTaskCycleRow() < TaskManager.CYCLE_TASK_MAX) {
				return 0;
			}
			return 1;
		default:
			return 0;
		}
	}
}
