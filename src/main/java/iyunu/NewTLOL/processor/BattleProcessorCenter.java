package iyunu.NewTLOL.processor;

import iyunu.NewTLOL.manager.BattleManager;
import iyunu.NewTLOL.message.BattleMessage;
import iyunu.NewTLOL.message.FightMessage;
import iyunu.NewTLOL.model.battle.BattleInfo;
import iyunu.NewTLOL.server.battle.BattleServer;

public class BattleProcessorCenter extends Thread {

	private volatile boolean process = true; // 是否运行
	private static final long PERIOD_WAIT = 100; // 无任务时，周期性探测是否有新任务到达
	private BattleInfo battleInfo; // 战斗信息对象

	/**
	 * 构造方法
	 * 
	 * @param battleInfo
	 *            战斗信息
	 */
	public BattleProcessorCenter(BattleInfo battleInfo) {
//		LogManager.info("【战斗处理器】已开启");
		this.battleInfo = battleInfo;
	}

	/**
	 * 检查轮数
	 * 
	 * @param turn
	 *            玩家提交轮数
	 * @return 检查正确
	 */
	public boolean checkTurn(int turn) {
		if (battleInfo.getTurn() == turn) {
			return true;
		}
		return false;
	}

	/**
	 * 关闭进程
	 */
	public void close() {
		process = false;
	}

	public void run() {
		while (process) {

			long startTime = System.currentTimeMillis(), spent = 0;

			if (startTime >= battleInfo.getWaitingTime()) {
				if (BattleServer.battle(battleInfo)) {
					BattleServer.resetHpAndMp(battleInfo);
					close();
				}

//				FightMessage.sendFightResult(battleInfo);
				
				if (battleInfo.getType() == 4) {
					FightMessage.sendFightResult(battleInfo);
				} else {
					BattleMessage.sendFightResult(battleInfo, battleInfo.getRight());
				}
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
		BattleManager.instance().removeBattleProcessorCenter(battleInfo.getBattleId());
//		LogManager.info("【战斗处理器】已关闭");
	}

	/**
	 * @return the battleInfo
	 */
	public BattleInfo getBattleInfo() {
		return battleInfo;
	}

}
