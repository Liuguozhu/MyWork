package iyunu.NewTLOL.processor.gangFight;

import iyunu.NewTLOL.manager.BulletinManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.gang.GangFightManager;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.log.LogManager;

/**
 * 帮派战循环赛
 * 
 * @author SunHonglei
 * 
 */
public class GangFightOutProcessorCenter extends Thread {

	private volatile boolean process = true; // 是否运行
	private static final long PERIOD_WAIT = 100; // 无任务时，周期性探测是否有新任务到达

	public static long 进场时间_8进4;
	public static long 开战时间_8进4;
	public static long 结束时间_8进4;

	public static long 进场时间_4进2;
	public static long 开战时间_4进2;
	public static long 结束时间_4进2;

	public static long 进场时间_决赛1;
	public static long 开战时间_决赛1;
	public static long 结束时间_决赛1;

	/**
	 * 关闭进程
	 */
	public void shutdown() {
		process = false;
	}

	public void run() {
		long now = System.currentTimeMillis();
		// 进场时间_8进4 = System.currentTimeMillis();
		// 开战时间_8进4 = 进场时间_8进4 + Time.MINUTE_MILLISECOND * 1;
		// 结束时间_8进4 = 开战时间_8进4 + Time.MINUTE_MILLISECOND * 1;
		// 进场时间_4进2 = 结束时间_8进4 + Time.MINUTE_MILLISECOND * 1;
		// 开战时间_4进2 = 进场时间_4进2 + Time.MINUTE_MILLISECOND * 1;
		// 结束时间_4进2 = 开战时间_4进2 + Time.MINUTE_MILLISECOND * 1;
		// 进场时间_决赛1 = 结束时间_4进2 + Time.MINUTE_MILLISECOND * 1;
		// 开战时间_决赛1 = 进场时间_决赛1 + Time.MINUTE_MILLISECOND * 1;
		// 结束时间_决赛1 = 开战时间_决赛1 + Time.MINUTE_MILLISECOND * 1;
		// 开战时间_决赛2 = 结束时间_决赛1 + Time.MINUTE_MILLISECOND * 1;
		// 结束时间_决赛2 = 开战时间_决赛2 + Time.MINUTE_MILLISECOND * 1;
		// 开战时间_决赛3 = 结束时间_决赛2 + Time.MINUTE_MILLISECOND * 1;
		// 结束时间_决赛3 = 开战时间_决赛3 + Time.MINUTE_MILLISECOND * 1;
		// 开战时间_8进4 += 进场时间_8进4 + Time.MINUTE_MILLISECOND * 5;
		// 结束时间_8进4 += 开战时间_8进4 + Time.MINUTE_MILLISECOND * 5;
		// 进场时间_4进2 += 结束时间_8进4 + Time.MINUTE_MILLISECOND * 5;
		// 开战时间_4进2 += 进场时间_4进2 + Time.MINUTE_MILLISECOND * 3;
		// 结束时间_4进2 += 开战时间_4进2 + Time.MINUTE_MILLISECOND * 5;
		// 进场时间_决赛1 += 结束时间_4进2 + Time.MINUTE_MILLISECOND * 9;
		// 开战时间_决赛1 += 进场时间_决赛1 + Time.MINUTE_MILLISECOND * 3;
		// 结束时间_决赛1 += 开战时间_决赛1 + Time.MINUTE_MILLISECOND * 5;
		// 开战时间_决赛2 += 结束时间_决赛1 + Time.MINUTE_MILLISECOND * 5;
		// 结束时间_决赛2 += 开战时间_决赛2 + Time.MINUTE_MILLISECOND * 5;
		// 开战时间_决赛3 += 结束时间_决赛2 + Time.MINUTE_MILLISECOND * 5;
		// 结束时间_决赛3 += 开战时间_决赛3 + Time.MINUTE_MILLISECOND * 5;

		int result = GangFightManager.生成淘汰赛对阵表();
		int state = 0;

		switch (result) {
		case 0: // 上届冠军继续保持
		case 1: // 直接冠军
			return;
		case 2: // 直接决赛
			state = 5;
			进场时间_决赛1 = now;
			开战时间_决赛1 = 进场时间_决赛1 + Time.MINUTE_MILLISECOND * 3;
			结束时间_决赛1 = 开战时间_决赛1 + Time.MINUTE_MILLISECOND * 5;

			GangFightManager.开战时间 = 开战时间_决赛1;
			GangFightManager.结束时间 = 结束时间_决赛1;
			GangFightManager.淘汰赛比赛场地(result);
			// CopyOfGangFightManager.state = 1;
			break;
		case 3: // 直接4进2
			state = 2;
			进场时间_4进2 = now;
			开战时间_4进2 = 进场时间_4进2 + Time.MINUTE_MILLISECOND * 5;
			结束时间_4进2 = 开战时间_4进2 + Time.MINUTE_MILLISECOND * 5;
			进场时间_决赛1 = 结束时间_4进2 + Time.MINUTE_MILLISECOND * 9;
			开战时间_决赛1 = 进场时间_决赛1 + Time.MINUTE_MILLISECOND * 3;
			结束时间_决赛1 = 开战时间_决赛1 + Time.MINUTE_MILLISECOND * 5;

			GangFightManager.开战时间 = 开战时间_4进2;
			GangFightManager.结束时间 = 结束时间_4进2;
			GangFightManager.淘汰赛比赛场地(result);
			// CopyOfGangFightManager.state = 1;
			break;
		case 4: // 8进4
			state = 0;
			进场时间_8进4 = now;
			开战时间_8进4 = 进场时间_8进4 + Time.MINUTE_MILLISECOND * 5;
			结束时间_8进4 = 开战时间_8进4 + Time.MINUTE_MILLISECOND * 5;
			进场时间_4进2 = 结束时间_8进4 + Time.MINUTE_MILLISECOND * 5;
			开战时间_4进2 = 进场时间_4进2 + Time.MINUTE_MILLISECOND * 5;
			结束时间_4进2 = 开战时间_4进2 + Time.MINUTE_MILLISECOND * 5;
			进场时间_决赛1 = 结束时间_4进2 + Time.MINUTE_MILLISECOND * 9;
			开战时间_决赛1 = 进场时间_决赛1 + Time.MINUTE_MILLISECOND * 3;
			结束时间_决赛1 = 开战时间_决赛1 + Time.MINUTE_MILLISECOND * 5;

			GangFightManager.开战时间 = 开战时间_8进4;
			GangFightManager.结束时间 = 结束时间_8进4;
			GangFightManager.淘汰赛比赛场地(result);
			// CopyOfGangFightManager.state = 1;
			break;
		default: // 无

		}

		// CopyOfGangFightManager.淘汰赛比赛场地(num);

		GangFightManager.GANG_FIGHT_STATE = 1;
		GangFightManager.FINISH_TIME = GangFightManager.开战时间;
		GangFightManager.STATE = 3;

		while (process) {
			long startTime = System.currentTimeMillis(), spent = 0;

			if (state == 0 && startTime >= 开战时间_8进4) {
				state = 1;
				GangFightManager.FINISH_TIME = 结束时间_8进4;
				GangFightManager.STATE = 4;
				
				for (Long roleId : GangFightManager.参战角色编号) {
					if (ServerManager.instance().isOnline(roleId)) {
						Role role = ServerManager.instance().getOnlinePlayer(roleId);
						RoleServer.addGangActivity(role, 40);
					}
				}
				GangFightManager.参战角色编号.clear();
			}

			if (state == 1 && startTime >= 结束时间_8进4) {
				state = 2;
				GangFightManager.FINISH_TIME = 进场时间_4进2;
				GangFightManager.STATE = 2;

				GangFightManager.开战时间 = 开战时间_4进2;
				GangFightManager.结束时间 = 结束时间_4进2;
				
				GangFightManager.淘汰赛比赛场地(3);
			}

			if (state == 2 && startTime >= 进场时间_4进2) {
				state = 3;
				GangFightManager.FINISH_TIME = 开战时间_4进2;
				GangFightManager.STATE = 3;
				BulletinManager.instance().addBulletinRock("帮派战半决赛已经开始！请报名的帮派立即前往征战！", 1);
			}
			if (state == 3 && startTime >= 开战时间_4进2) {
				state = 4;
				GangFightManager.FINISH_TIME = 结束时间_4进2;
				GangFightManager.STATE = 4;
				
				for (Long roleId : GangFightManager.参战角色编号) {
					if (ServerManager.instance().isOnline(roleId)) {
						Role role = ServerManager.instance().getOnlinePlayer(roleId);
						RoleServer.addGangActivity(role, 40);
					}
				}
				GangFightManager.参战角色编号.clear();
			}
			if (state == 4 && startTime >= 结束时间_4进2) {
				state = 5;
				GangFightManager.FINISH_TIME = 进场时间_决赛1;
				GangFightManager.STATE = 2;

				GangFightManager.开战时间 = 开战时间_决赛1;
				GangFightManager.结束时间 = 结束时间_决赛1;
				
				GangFightManager.淘汰赛比赛场地(2);
			}

			if (state == 5 && startTime >= 进场时间_决赛1) {
				state = 6;
				GangFightManager.FINISH_TIME = 开战时间_决赛1;
				GangFightManager.STATE = 3;
				BulletinManager.instance().addBulletinRock("帮派战决赛第一轮已经开始！请报名的帮派立即前往征战！", 1);
			}
			if (state == 6 && startTime >= 开战时间_决赛1) {
				state = 7;
				GangFightManager.FINISH_TIME = 结束时间_决赛1;
				GangFightManager.STATE = 4;
				
				for (Long roleId : GangFightManager.参战角色编号) {
					if (ServerManager.instance().isOnline(roleId)) {
						Role role = ServerManager.instance().getOnlinePlayer(roleId);
						RoleServer.addGangActivity(role, 40);
					}
				}
				GangFightManager.参战角色编号.clear();
			}
			if (state == 7 && startTime >= 结束时间_决赛1) {
				GangFightManager.GANG_FIGHT_STATE = 2;
				GangFightManager.STATE = 0;

				GangFightManager.FINISH_TIME =Time.getNextTime(20, 0, 0);
				process = false;
				break;
			}

			spent = System.currentTimeMillis() - startTime;
			if (spent < PERIOD_WAIT) {
				synchronized (this) {
					try {
						wait(PERIOD_WAIT);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		LogManager.info("【帮派战淘汰赛处理器】已关闭");
	}

}
