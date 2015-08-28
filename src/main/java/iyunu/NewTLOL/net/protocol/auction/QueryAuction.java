package iyunu.NewTLOL.net.protocol.auction;

import iyunu.NewTLOL.enumeration.EColor;
import iyunu.NewTLOL.manager.AuctionManager;
import iyunu.NewTLOL.model.auction.Auction;
import iyunu.NewTLOL.model.auction.EAuctionType;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.Time;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 拍卖列表
 * @author SunHonglei
 *
 */
public class QueryAuction extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "请求成功";
	private List<Auction> auctionPage = new ArrayList<Auction>();
	private ArrayList<Long> category = null;
	private EAuctionType type;
	public int page_max = 8;

	int page = 0;
	int totalpage = 0;

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======数据重置======
		result = 0;
		reason = "请求成功";
		auctionPage.clear();
		page = 1;
		totalpage = 1;

		// ======获取参数======
		type = EAuctionType.values()[msg.readInt("type")];
		page = msg.readInt("page");
		// 检查page
		if (page < 0 || type == null) {
			reason = "数据错误";
			result = 1;
			return;
		} else {
			getAuctions(page);
		}
	}

	public void sort(ArrayList<Long> category, EAuctionType type) {
		try {

			switch (type) {
			case partner:
				Collections.sort(category, new Comparator<Long>() {
					@Override
					public int compare(Long o1, Long o2) {
						Auction auction1 = AuctionManager.instance().getAuction(o1);
						Auction auction2 = AuctionManager.instance().getAuction(o2);

						if (auction1.getPartner().getColor().ordinal() > auction2.getPartner().getColor().ordinal()) {
							return -1;
						} else if (auction1.getPartner().getColor().ordinal() < auction2.getPartner().getColor().ordinal()) {
							return 1;
						} else {
							if (auction1.getPartner().getLevel() > auction2.getPartner().getLevel()) {
								return -1;
							} else if (auction1.getPartner().getLevel() < auction2.getPartner().getLevel()) {
								return 1;
							} else {
								if (auction1.getBuyoutprice() < auction2.getBuyoutprice()) {
									return -1;
								} else if (auction1.getBuyoutprice() > auction2.getBuyoutprice()) {
									return 1;
								}

								return 0;
							}

						}
					}
				});
				break;
			case money:
				Collections.sort(category, new Comparator<Long>() {
					@Override
					public int compare(Long o1, Long o2) {
						Auction auction1 = AuctionManager.instance().getAuction(o1);
						Auction auction2 = AuctionManager.instance().getAuction(o2);
						double single1 = auction1.getBuyoutprice() * 1.0 / auction1.getMoney();
						double single2 = auction2.getBuyoutprice() * 1.0 / auction2.getMoney();
						if (single1 < single2) {
							return -1;
						} else if (single1 > single2) {
							return 1;
						} else {
							if (auction1.getMoney() > auction2.getMoney()) {
								return -1;
							} else if (auction1.getMoney() < auction2.getMoney()) {
								return 1;
							}
							return 0;
						}
					}
				});
				break;
			default:
				Collections.sort(category, new Comparator<Long>() {
					@Override
					public int compare(Long o1, Long o2) {
						Auction auction1 = AuctionManager.instance().getAuction(o1);
						Auction auction2 = AuctionManager.instance().getAuction(o2);

						if (auction1.getItem().getColor().ordinal() > auction2.getItem().getColor().ordinal()) {
							return -1;
						} else if (auction1.getItem().getColor().ordinal() < auction2.getItem().getColor().ordinal()) {
							return 1;
						} else {
							if (auction1.getItem().getLevel() > auction2.getItem().getLevel()) {
								return -1;
							} else if (auction1.getItem().getLevel() < auction2.getItem().getLevel()) {
								return 1;
							} else {
								double single1 = auction1.getBuyoutprice() * 1.0 / auction1.getNum();
								double single2 = auction2.getBuyoutprice() * 1.0 / auction2.getNum();
								if (single1 < single2) {
									return -1;
								} else if (single1 > single2) {
									return 1;
								}
								return 0;
							}

						}
					}
				});
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getAuctions(int page) {
		// 取得分类的产品ID List
		category = type.getList();

		if (category.size() > 0) {
			sort(category, type);
			int start = (page - 1) * page_max + 1;
			int end = start + page_max > category.size() ? category.size() + 1 : start + page_max;

			totalpage = (category.size() - 1) / page_max + 1;
			totalpage = totalpage == 0 ? 1 : totalpage;// 总页数

			// 把分布好的LIST 截取
			LinkedHashMap<Long, Auction> a = AuctionManager.instance().getAuctionMap();
			List<Long> sub = category.subList(start - 1, end - 1);
			for (int i = 0; i < sub.size(); i++) {
				auctionPage.add(a.get(sub.get(i)));
			}
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_auction");
			message.write("result", result);
			if (result == 0) {
				if (auctionPage != null) {
					message.write("totalpage", totalpage);
					message.write("viewpage", page);
					long now = System.currentTimeMillis();
					for (int i = 0; i < auctionPage.size(); i++) {
						Auction auction = auctionPage.get(i);
						// if (auction != null) {
						LlpMessage msg = message.write("auctions");
						msg.write("id", auction.getId());
						msg.write("tid", AuctionManager.instance().getTid(auction));

						msg.write("name", auction.getTitle());

						msg.write("timeout", Time.timeOut(auction.getTimeout(), now));
						msg.write("ownername", auction.getOwnerName());
						msg.write("buyoutprice", auction.getBuyoutprice());
						if (auction.getType() == EAuctionType.money) {
							msg.write("color", EColor.orange.ordinal());
							msg.write("level", 0);
						} else if (auction.getType() == EAuctionType.partner) {
							msg.write("color", auction.getPartner().getColor().ordinal());
							msg.write("level", auction.getPartner().getLevel());
						} else {
							msg.write("color", auction.getItem().getColor().ordinal());
							msg.write("level", auction.getItem().getLevel());
						}
						int type = AuctionManager.instance().getReturnType(auction);
						msg.write("type", type);
						if (type == 0) {
							if (auction.getItem() != null && auction.getItem() instanceof Equip) {
								Equip equip = (Equip) auction.getItem();
								msg.write("star", equip.getStar());
							} else {
								msg.write("star", 0);
							}
						} else {
							msg.write("star", 0);
						}
					}
				}
				message.write("reason", reason);
			}
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
