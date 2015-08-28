package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.processor.BattleProcessorCenter;

import java.util.HashMap;
import java.util.Map;

public class BattleManager {

	/**
	 * 私有构造方法
	 */
	private BattleManager() {

	}

	private static BattleManager instance = new BattleManager();

	/**
	 * 获取战斗管理实例
	 * 
	 * @return 战斗管理实例
	 */
	public static BattleManager instance() {
		return instance;
	}

	private long battleId = 1; // 战斗编号计数器
	private Map<Long, BattleProcessorCenter> battleTaskMap = new HashMap<Long, BattleProcessorCenter>(); // 战斗处理中心集合
//	private Map<Long, BattleProcessorCenter> battleTaskMap = new WeakHashMap<Long, BattleProcessorCenter>(); // 战斗处理中心集合
	public static final int BATTLE_DURATION = 65 * 1000; // 每回合等待时间

	/**
	 * 获取战斗编号
	 * 
	 * @return 战斗编号
	 */
	public long getBattleId() {
		return battleId++;
	}

	/**
	 * 添加战斗处理中心
	 * 
	 * @param battleInfo
	 *            战斗信息
	 */
	public void addBattleProcessor(BattleProcessorCenter battleProcessorCenter) {
		battleTaskMap.put(battleProcessorCenter.getBattleInfo().getBattleId(), battleProcessorCenter);
		battleProcessorCenter.start();
//		LogManager.exception("BattleManager*********battleTaskMap********" + battleTaskMap.size());
	}

	/**
	 * 获取战斗处理中心
	 * 
	 * @param battleId
	 *            战斗编号
	 * @return 战斗处理中心
	 */
	public BattleProcessorCenter getBattleProcessorCenter(Long battleId) {
		return battleTaskMap.get(battleId);
	}

	/**
	 * 删除战斗处理中心
	 * 
	 * @param battleId
	 *            战斗编号
	 */
	public void removeBattleProcessorCenter(Long battleId) {
		battleTaskMap.remove(battleId);
	}

	/**
	 * 关闭所有战斗处理器
	 */
	public void stopBattleProcessor() {
		for (BattleProcessorCenter battleProcessorCenter : battleTaskMap.values()) {
			battleProcessorCenter.close();
		}
	}

}
