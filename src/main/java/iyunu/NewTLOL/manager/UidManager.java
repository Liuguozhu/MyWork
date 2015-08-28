package iyunu.NewTLOL.manager;

/**
 * 唯一编号获取器
 * 
 * @author SunHonglei
 * 
 */
public class UidManager {

	/**
	 * 私有构造方法
	 */
	private UidManager() {

	}

	private static UidManager instance = new UidManager();
	private long last;
	private long auctionLast;
	private long orderNumLast;
	private long last_fabuling;

	public static UidManager instance() {
		return instance;
	}

	/**
	 * 获取唯一编号
	 * 
	 * @return 唯一编号
	 */
	public synchronized long uid() {
		long now = System.currentTimeMillis();
		while (now == this.last) {
			now = System.currentTimeMillis();
		}
		this.last = now;
		return now;
	}

	/**
	 * 获取拍卖行唯一编号
	 * 
	 * @return 唯一编号
	 */
	public synchronized long auctionUid() {
		long now = System.currentTimeMillis();
		while (now == this.auctionLast) {
			now = System.currentTimeMillis();
		}
		this.auctionLast = now;
		return now;
	}

	/**
	 * 获取充值订单号
	 * 
	 * @return 唯一编号
	 */
	public synchronized long orderNum() {
		long now = System.currentTimeMillis();
		while (now == this.orderNumLast) {
			now = System.currentTimeMillis();
		}
		this.orderNumLast = now;
		return now;
	}

	/**
	 * 获取物品唯一编号
	 * 
	 * @return 物品唯一编号
	 */
	public synchronized String ItemUid() {
		return ServerManager.instance().getServer() + UidManager.instance().uid();
	}

	/**
	 * 获取唯一编号
	 * 
	 * @return 唯一编号
	 */
	public synchronized long uid_fabuling() {
		long now = System.currentTimeMillis();
		while (now == this.last_fabuling) {
			now = System.currentTimeMillis();
		}
		this.last_fabuling = now;
		return now;
	}
}
