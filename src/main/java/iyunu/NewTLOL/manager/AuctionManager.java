package iyunu.NewTLOL.manager;

import iyunu.NewTLOL.base.spring.Spring;
import iyunu.NewTLOL.dao.BaseDao;
import iyunu.NewTLOL.enumeration.common.Eauction;
import iyunu.NewTLOL.model.auction.Auction;
import iyunu.NewTLOL.model.auction.EAuctionType;
import iyunu.NewTLOL.model.item.EItem;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.role.Role;
import iyunu.NewTLOL.server.mail.MailServer;
import iyunu.NewTLOL.service.iface.auction.AuctionService;
import iyunu.NewTLOL.util.Translate;
import iyunu.NewTLOL.util.json.JsonTool;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

public class AuctionManager {

	/**
	 * 私有构造方法
	 */
	private AuctionManager() {
	}

	private static AuctionManager instance = new AuctionManager();

	/**
	 * 获取拍卖行管理实例
	 * 
	 * @return 拍卖行管理实例
	 */
	public static AuctionManager instance() {
		return instance;
	}

	public LinkedHashMap<Long, Auction> auctions = new LinkedHashMap<Long, Auction>(); // 拍卖行数据<拍卖物品编号，拍卖物品对象>

	/** 下架检查执行器 */
	public ScheduledThreadPoolExecutor down = new ScheduledThreadPoolExecutor(1);
	public static final int OFTEN = 1 * 60;// 每分钟检查一次是否有到时下架
	/** 最大上架的物品 **/
	public static final int AUCTION_MAX = 4;

	/**
	 * 把字符串转为Item 对象
	 * 
	 * @param str
	 * @return Item
	 */
	public Item parseItem(String str) {
		if (str == null || "".equals(str)) {
			return null;
		} else {
			String[] strs = str.split("#");
			EItem eItem = EItem.values()[Translate.stringToInt(strs[0])];
			Item item = JsonTool.decode(strs[1], eItem.getObject());
			return item;
		}
	}

	/**
	 * @return Item转为 jason String
	 */
	public String itemToJaSon(Item item) {
		if (item != null) {
			String str = item.getType().ordinal() + "#";
			SerializeWriter out = new SerializeWriter();
			JSONSerializer serializer = new JSONSerializer(out);
			serializer.write(item);
			str += serializer.toString();
			return str;
		} else {
			return "";
		}
	}

	/**
	 * 加载拍卖行
	 */
	public void load() {
		List<Auction> auctionList = BaseDao.instance().getAuctionDao().query();
		for (int i = 0; i < auctionList.size(); i++) {
			Auction auction = auctionList.get(i);
			switch (auction.getType()) {
			case money: // 元宝和伙伴，什么也不做
			case partner:
				break;
			default:
				// 把itemStr转回ITEM对象
				Item it = parseItem(auction.getItemStr());
				auction.setItem(it);
				break;
			}

			auctions.put(auction.getId(), auction);
			// 加入分类索引
			auction.getType().add(auction.getId());
		}
		// 每分钟检查到时下架
		down.scheduleWithFixedDelay(new DownSchedule(), 0, OFTEN, TimeUnit.SECONDS);
		auctionList.clear();
		LogManager.info("【拍卖】加载完成");
	}

	/**
	 * 每分钟执行一次检查下架
	 * 
	 * @author fenghaiyu
	 * 
	 */
	class DownSchedule implements Runnable {
		public void run() {
			try {
				Iterator<Auction> it = auctions.values().iterator();
				while (it.hasNext()) {
					Auction auction = it.next();
					if (auction.getTimeout() <= System.currentTimeMillis()) {
						// 从type中删除
						auction.getType().minus(auction.getId());
						// 从集合中删除
						it.remove();

						// 删除我的拍卖中的
						long roleId = auction.getOwnerId();
						Role role = ServerManager.instance().getOnlinePlayer(roleId);

						if (role != null) {
							Iterator<Auction> its = role.getAuctions().iterator();
							while (its.hasNext()) {
								Auction auction1 = its.next();
								if (auction.getId() == auction1.getId()) {
									its.remove();
								}
							}
						}
						AuctionService auctionService = Spring.instance().getBean("auctionService", AuctionService.class);
						auctionService.delete(auction);
						LogManager.auction(auction.getOwnerId(), "", auction, Eauction.timeout.ordinal());
						// 邮件返回
						if (auction.getType() == EAuctionType.money) {
							MailServer.send(auction.getOwnerId(), "到时下架", "请查收", null, 0, 0, 0, auction.getMoney(), 0, null, "拍卖行");
						} else if (auction.getType() == EAuctionType.partner) {
							MailServer.send(auction.getOwnerId(), "到时下架", "请查收", null, 0, 0, 0, 0, 0, auction.getPartner(), "拍卖行");
						} else {
							MailServer.send(auction.getOwnerId(), "到时下架", "请查收", auction.getItem(), auction.getNum(), 0, 0, 0, 0, null, "拍卖行");
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * 命令下架所有拍卖
	 * */
	public void downByAdmin() {

		try {
			Iterator<Auction> it = auctions.values().iterator();
			while (it.hasNext()) {
				Auction auction = it.next();
				// 从type中删除
				auction.getType().minus(auction.getId());
				// 从集合中删除
				it.remove();

				// 删除我的拍卖中的
				long roleId = auction.getOwnerId();
				Role role = ServerManager.instance().getOnlinePlayer(roleId);

				if (role != null) {
					Iterator<Auction> its = role.getAuctions().iterator();
					while (its.hasNext()) {
						Auction auction1 = its.next();
						if (auction.getId() == auction1.getId()) {
							its.remove();
						}
					}
				}
				AuctionService auctionService = Spring.instance().getBean("auctionService", AuctionService.class);
				auctionService.delete(auction);
				LogManager.auction(auction.getOwnerId(), "", auction, Eauction.timeout.ordinal());
				// 邮件返回
				if (auction.getType() == EAuctionType.money) {
					MailServer.send(auction.getOwnerId(), "到时下架", "请查收", null, 0, 0, 0, auction.getMoney(), 0, null, "拍卖行");
				} else if (auction.getType() == EAuctionType.partner) {
					MailServer.send(auction.getOwnerId(), "到时下架", "请查收", null, 0, 0, 0, 0, 0, auction.getPartner(), "拍卖行");
				} else {
					MailServer.send(auction.getOwnerId(), "到时下架", "请查收", auction.getItem(), auction.getNum(), 0, 0, 0, 0, null, "拍卖行");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除拍卖
	 * 
	 * @param auction
	 *            要删除的拍卖对象
	 * @return
	 */
	public void delete(Auction auction, Role role) {
		Long auctionId = auction.getId();
		auction.getType().minus(auctionId);
		if (auctions.containsKey(auctionId)) {
			auctions.remove(auction.getId());
		}
		if (role != null) {
			Iterator<Auction> it = role.getAuctions().iterator();
			while (it.hasNext()) {
				Auction auction1 = it.next();
				if (auction.getId() == auction1.getId()) {
					it.remove();
				}
			}

		}
	}

	/**
	 * 添加拍卖品
	 * */
	public boolean add(Auction auction) {
		if (auction.getType().add(auction.getId())) {
			auctions.put(auction.getId(), auction);
			return true;
		}
		return false;
	}

	/**
	 * @param auction
	 *            拍卖对象
	 * @return 拍卖对象所对应 应该返回的tid，元宝是0，伙伴是partnerid,物品是itemid
	 */
	public long getTid(Auction auction) {
		EAuctionType t = auction.getType();
		switch (t) {
		case partner:
			return auction.getPartnerId();
		case money:
			return 0;
		default:
			return auction.getItem().getId();
		}
	}

	/**
	 * @param auction
	 *            拍卖对象
	 * @return 拍卖对象所对应 ，元宝是2，伙伴是1,物品是0
	 */
	public int getReturnType(Auction auction) {
		EAuctionType t = auction.getType();
		switch (t) {
		case partner:
			return 1;
		case money:
			return 2;
		default:
			return 0;
		}
	}

	/**
	 * 获取拍卖对象
	 * 
	 * @param auctionId
	 *            拍卖编号
	 * @return 拍卖对象
	 */
	public Auction getAuction(Long auctionId) {
		if (auctions.containsKey(auctionId)) {
			return auctions.get(auctionId);
		}
		return null;
	}

	/**
	 * 上线时加载“我的拍卖” 和我的拍卖记录
	 * */
	public void loadMyAuction(Role role) {
		// 加载我的拍卖
		AuctionService auctionService = Spring.instance().getBean("auctionService", AuctionService.class);
		List<Auction> list = auctionService.queryMyAuction(role);
		for (int i = 0; i < list.size(); i++) {
			Auction auction = list.get(i);
			// 把itemStr转回ITEM对象

			switch (auction.getType()) {
			case money: // 元宝和伙伴，什么也不做
			case partner:
				break;
			default:
				// 把itemStr转回ITEM对象
				Item it = parseItem(auction.getItemStr());
				auction.setItem(it);
				break;
			}
		}
		role.getAuctions().addAll(list);
	}

	/**
	 * @return the auctionMap
	 */
	public LinkedHashMap<Long, Auction> getAuctionMap() {
		return auctions;
	}

}