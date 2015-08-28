package iyunu.NewTLOL.service.impl.auction;

import iyunu.NewTLOL.dao.BaseDao;
import iyunu.NewTLOL.model.auction.Auction;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.service.iface.auction.AuctionService;

import java.util.List;

public class AuctionServiceImpl implements AuctionService {

	@Override
	public void insert(Auction auction) {
		BaseDao.instance().getAuctionDao().insert(auction);

	}

	@Override
	public void delete(Auction auction) {
		BaseDao.instance().getAuctionDao().delete(auction);
	}

	@Override
	public List<Auction> queryMyAuction(Role role) {
		return BaseDao.instance().getAuctionDao().queryMyAuction(role);
	}

//	@Override
//	public int getGold(Role role) {
//		return BaseDao.instance().getAuctionDao().getGold(role);
//	}
//
//	@Override
//	public void addGold(int gold, Role role) {
//		BaseDao.instance().getAuctionDao().addGold(gold, role);
//	}
//
//	@Override
//	public void minusGold(Role role) {
//		BaseDao.instance().getAuctionDao().minusGold(role);
//	}

}
