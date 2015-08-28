package iyunu.NewTLOL.manager;


public class BagManager {
	/**
	 * 私有构造方法
	 */
	private BagManager() {
	}

	private static BagManager instance = new BagManager();

	/**
	 * 获取BagManager对象
	 * 
	 * @return BagManager对象
	 */
	public static BagManager instance() {
		return instance;
	}

	/** 扩展一个背包或仓库消耗元宝数量 **/
	public static final int USEMONEY = 2;

}
