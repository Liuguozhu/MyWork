package iyunu.NewTLOL.manager;


public class WarehouseManager {
	/**
	 * 私有构造方法
	 */
	private WarehouseManager() {
	}

	private static WarehouseManager instance = new WarehouseManager();

	/**
	 * 获取BagManager对象
	 * 
	 * @return BagManager对象
	 */
	public static WarehouseManager instance() {
		return instance;
	}

	/** 扩展一个背包或仓库消耗元宝数量 **/
	public static final int USEMONEY = 2;

}
