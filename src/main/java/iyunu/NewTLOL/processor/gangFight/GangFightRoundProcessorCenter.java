package iyunu.NewTLOL.processor.gangFight;

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
public class GangFightRoundProcessorCenter extends Thread {

	private volatile boolean process = true; // 是否运行
	private static final long PERIOD_WAIT = 100; // 无任务时，周期性探测是否有新任务到达

	/**
	 * 关闭进程
	 */
	public void shutdown() {
		process = false;
	}

	public void run() {

		long 进场时间 = System.currentTimeMillis();
		long 开战时间 = 进场时间 + Time.MINUTE_MILLISECOND * 5;
		long 结束时间 = 开战时间 + Time.MINUTE_MILLISECOND * 5;

		GangFightManager.开战时间 = 开战时间;
		GangFightManager.结束时间 = 结束时间;
		
		GangFightManager.生成循环赛列表();
		GangFightManager.循环赛比赛场地();
		
		GangFightManager.GANG_FIGHT_STATE = 0;
		GangFightManager.FINISH_TIME = 开战时间;
		GangFightManager.STATE = 3;
		
		
		int state = 0;
		while (process) {
			long startTime = System.currentTimeMillis(), spent = 0;

			if (state ==0 && startTime >= 开战时间) {
				for (Long roleId : GangFightManager.参战角色编号) {
					if (ServerManager.instance().isOnline(roleId)) {
						Role role = ServerManager.instance().getOnlinePlayer(roleId);
						RoleServer.addGangActivity(role, 40);
					}
				}
				GangFightManager.参战角色编号.clear();
				
				GangFightManager.FINISH_TIME = 结束时间;
				GangFightManager.STATE = 4;
				state = 1;
			}

			if (state ==1 && startTime >= 结束时间) {
				GangFightManager.GANG_FIGHT_STATE = 2;
				GangFightManager.STATE = 0;
				
				GangFightManager.周积分排序();
				GangFightManager.FINISH_TIME = 开战时间 + Time.DAY_MILLISECOND;
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
		LogManager.info("【帮派战循环赛处理器】已关闭");
	}

}
