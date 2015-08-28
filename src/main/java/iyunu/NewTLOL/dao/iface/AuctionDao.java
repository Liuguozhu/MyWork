package iyunu.NewTLOL.dao.iface;

import iyunu.NewTLOL.model.auction.Auction;
import iyunu.NewTLOL.model.role.Role;

import java.util.List;

public interface AuctionDao {
//	/**
//	 * 取得拍卖的金币
//	 * */
//	int getGold(Role role);
//
//	/**
//	 * 加金币
//	 * */
//	void addGold(int gold, Role role);

	/**
	 * 减金币
	 * */
//	void minusGold(Role role);

	/**
	 * 查询拍卖行信息
	 * 
	 * @param auction
	 */
	List<Auction> query();

	/**
	 * 查询我的拍卖
	 * */
	List<Auction> queryMyAuction(Role role);

	/**
	 * 插入新添加的物品
	 * */
	void insert(Auction auction);

	/**
	 * 删除拍卖
	 * */
	void delete(Auction auction);

}
