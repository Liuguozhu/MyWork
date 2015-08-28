package iyunu.NewTLOL.redis;

import org.springframework.data.redis.support.atomic.RedisAtomicLong;

public class RedisCenter {

	private RedisAtomicLong roleCounter;
	private RedisAtomicLong auctionCounter;
	private RedisAtomicLong mailCounter;
	private RedisAtomicLong gangCounter;
	private RedisAtomicLong partnerCounter;
	private RedisAtomicLong payCounter;

	/**
	 * 获取角色编号
	 * 
	 * @return 角色编号
	 */
	public long getRoleId() {
		return roleCounter.incrementAndGet();
	}

	/**
	 * 获取拍卖编号
	 * 
	 * @return 拍卖编号
	 */
	public long getAuctionId() {
		return auctionCounter.incrementAndGet();
	}

	/**
	 * 获取邮件编号
	 * 
	 * @return 邮件编号
	 */
	public long getMailId() {
		return mailCounter.incrementAndGet();
	}

	/**
	 * 获取帮派编号
	 * 
	 * @return 帮派编号
	 */
	public long getGangId() {
		return gangCounter.incrementAndGet();
	}

	/**
	 * 获取伙伴编号
	 * 
	 * @return 伙伴编号
	 */
	public long getPartnerId() {
		return partnerCounter.incrementAndGet();
	}

	/**
	 * 获取安智充值订单号
	 * 
	 * @return 充值订单号
	 */
	public String getTradeNo() {
		return String.valueOf(payCounter.incrementAndGet());
	}

	/**
	 * @return the auctionCounter
	 */
	public RedisAtomicLong getAuctionCounter() {
		return auctionCounter;
	}

	/**
	 * @param auctionCounter
	 *            the auctionCounter to set
	 */
	public void setAuctionCounter(RedisAtomicLong auctionCounter) {
		this.auctionCounter = auctionCounter;
	}

	/**
	 * @return the mailCounter
	 */
	public RedisAtomicLong getMailCounter() {
		return mailCounter;
	}

	/**
	 * @param mailCounter
	 *            the mailCounter to set
	 */
	public void setMailCounter(RedisAtomicLong mailCounter) {
		this.mailCounter = mailCounter;
	}

	/**
	 * @return the gangCounter
	 */
	public RedisAtomicLong getGangCounter() {
		return gangCounter;
	}

	/**
	 * @param gangCounter
	 *            the gangCounter to set
	 */
	public void setGangCounter(RedisAtomicLong gangCounter) {
		this.gangCounter = gangCounter;
	}

	/**
	 * @return the partnerCounter
	 */
	public RedisAtomicLong getPartnerCounter() {
		return partnerCounter;
	}

	/**
	 * @param partnerCounter
	 *            the partnerCounter to set
	 */
	public void setPartnerCounter(RedisAtomicLong partnerCounter) {
		this.partnerCounter = partnerCounter;
	}

	/**
	 * @return the payCounter
	 */
	public RedisAtomicLong getPayCounter() {
		return payCounter;
	}

	/**
	 * @param payCounter
	 *            the payCounter to set
	 */
	public void setPayCounter(RedisAtomicLong payCounter) {
		this.payCounter = payCounter;
	}

	/**
	 * @return the roleCounter
	 */
	public RedisAtomicLong getRoleCounter() {
		return roleCounter;
	}

	/**
	 * @param roleCounter
	 *            the roleCounter to set
	 */
	public void setRoleCounter(RedisAtomicLong roleCounter) {
		this.roleCounter = roleCounter;
	}

}