package iyunu.NewTLOL.processor.activity;

import iyunu.NewTLOL.manager.PayExchangeManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.util.log.LogManager;

/**
 * 积分榜
 * 
 * @author fenghaiyu
 * 
 */
public class PayExchangeProcessorCenter extends Thread {

	private volatile boolean process = true; // 是否运行
	private static final long PERIOD_WAIT = 100; // 无任务时，周期性探测是否有新任务到达

	/**
	 * 关闭进程
	 */
	public void shutdown() {
		process = false;
	}

	public void run() {

		while (process) {
			long startTime = System.currentTimeMillis();
			long spent = 0;
			// 如果处于时间段之内且活动开关为关闭
			if (startTime >= PayExchangeManager.STARTTIME && startTime < PayExchangeManager.ENDTIME && !PayExchangeManager.FLAG) {
				PayExchangeManager.FLAG = true;
				System.out.println("=====开始");
				// 打开积分榜图标
				for (Long roleId : ServerManager.instance().getIdChannelPair().keySet()) {
					Role role = ServerManager.instance().getOnlinePlayer(roleId);
					SendMessage.refreshPayExchange(role, 1);
				}
			}
			// 如果活动开关为打开，并且到时，则结算
			if (PayExchangeManager.FLAG && startTime >= PayExchangeManager.ENDTIME) {
				System.out.println("====结束");
				PayExchangeManager.FLAG = false;
				// 发奖励，清空
				PayExchangeManager.instance().sendAward();
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
		LogManager.info("【积分榜处理器】已关闭");
	}

}
