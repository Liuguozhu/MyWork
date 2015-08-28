package iyunu.NewTLOL.service.iface.auction;

import iyunu.NewTLOL.model.auction.Auction;
import iyunu.NewTLOL.model.role.Role;

import java.util.List;

public interface AuctionService {

//	int getGold(Role role);

//	void addGold(int gold, Role role);

//	void minusGold(Role role);

	void insert(Auction auction);

	void delete(Auction auction);

	/**
	 * 查询我的拍卖
	 * */
	List<Auction> queryMyAuction(Role role);
}
