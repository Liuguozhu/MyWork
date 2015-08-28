package iyunu.NewTLOL.model.io.auction;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.model.auction.Auction;
import iyunu.NewTLOL.service.iface.auction.AuctionService;

public class AuctionIOTask {

	private EAuctionIOTask id; // 任务编号
	private Auction auction; // 拍卖对象
	private long roleId; // 角色编号

	/**
	 * 构造方法
	 * 
	 * @param id
	 *            任务编号
	 * @param role
	 *            角色对象
	 */
	public AuctionIOTask(EAuctionIOTask id, Auction auction, long roleId, long mailId) {
		this.id = id;
		this.auction = auction;
		this.roleId = roleId;
	}

	/**
	 * 执行方法
	 */
	public void excute() {
		AuctionService auctionService = Spring.instance().getBean("auctionService", AuctionService.class);
		switch (id) {
		case insert:
			auctionService.insert(auction);
			break;
		case delete:
			auctionService.delete(auction);
			break;
		case query:
			break;
		case queryMyAuction:
			break;
		default:
			break;
		}
	}

	/**
	 * @return the id
	 */
	public EAuctionIOTask getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(EAuctionIOTask id) {
		this.id = id;
	}

	/**
	 * @return the auction
	 */
	public Auction getAuction() {
		return auction;
	}

	/**
	 * @param auction
	 *            the auction to set
	 */
	public void setAuction(Auction auction) {
		this.auction = auction;
	}

	/**
	 * @return the roleId
	 */
	public long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 *            the roleId to set
	 */
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

}
