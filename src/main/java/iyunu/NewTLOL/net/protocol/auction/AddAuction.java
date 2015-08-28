package iyunu.NewTLOL.net.protocol.auction;

import iyunu.NewTLOL.enumeration.common.Eauction;
import iyunu.NewTLOL.manager.AuctionManager;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.UidManager;
import iyunu.NewTLOL.message.BagMessage;
import iyunu.NewTLOL.model.auction.Auction;
import iyunu.NewTLOL.model.auction.EAuctionType;
import iyunu.NewTLOL.model.bag.Cell;
import iyunu.NewTLOL.model.item.EItemCost;
import iyunu.NewTLOL.model.item.Item;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.Time;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.HashMap;
import java.util.Map;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class AddAuction extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "上架物品成功";
	private Auction auction = null;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "上架物品成功";

		int num = msg.readInt("num");
		int buyoutprice = msg.readInt("buyoutprice");
		int cellId = msg.readInt("cellId");
		int type = msg.readInt("type");

		if (num <= 0) {
			result = 1;
			reason = "上架物品失败,请输入要拍卖的数量";
			return;
		}

		if (buyoutprice <= 0) {
			result = 1;
			reason = "上架物品失败,请输入要拍卖的价格";
			return;
		}

		if (buyoutprice > RoleManager.MAX_COIN) {
			result = 1;
			reason = "上架物品失败,不可超过" + RoleManager.MAX_COIN + "银两";
			return;
		}

		if (online.getAuctions().size() >= AuctionManager.AUCTION_MAX) {
			result = 1;
			reason = "您上架的物品已达到最大数量：" + AuctionManager.AUCTION_MAX + "个";
			return;
		}
		Cell cell = null;
		if (type == 1) {
			cell = online.getBag().getCells()[cellId];
		} else {
			cell = online.getBagStone().getCells()[cellId];
		}

		if (num > cell.getNum()) {
			result = 1;
			reason = "数量不足";
			return;
		}

		Item item = cell.getItem();
		if (item == null) {
			result = 1;
			reason = "物品不存在";
			return;
		}

		if (item.getIsDeal() == 1) {
			result = 1;
			reason = "此物品不能交易";
			return;
		}

		auction = new Auction();
		// 对象设置属性，准备插入数据库
		auction.setId(UidManager.instance().auctionUid());
		auction.setOwnerId(online.getId());
		auction.setOwnerName(online.getNick());
		auction.setNum(num);
		auction.setUid(item.getId());
		auction.setTid(0);
		auction.setBuyoutprice(buyoutprice);
		auction.setSrv(ServerManager.instance().getServer());
		auction.setTimeout(System.currentTimeMillis() + Time.DAY_MILLISECOND);
		auction.setItem(item);
		auction.setItemStr(AuctionManager.instance().itemToJaSon(item));
		auction.setTitle(auction.getItem().getName() + "×" + auction.getNum());

		// 加入拍卖行物品索引
		switch (item.getType()) {
		case equip:
			switch (((Equip) item).getPart()) {
			case belt:
			case coat:
			case shoe:
				auction.setType(EAuctionType.armor);
				break;
			case necklace:
			case ring:
				auction.setType(EAuctionType.jewelry);
				break;
			case weapon:
				auction.setType(EAuctionType.weapon);
				break;
			case shenbing:
				auction.setType(EAuctionType.shenbing);
				break;
			default:
				auction.setType(EAuctionType.other);
				break;
			}
			break;
		case drug:
		case drugP:
		case buffHp:
		case buffMp:
		case buffPartnerHp:
			auction.setType(EAuctionType.drug);
			break;
		case book:
			auction.setType(EAuctionType.book);
			break;
		case stone:
			auction.setType(EAuctionType.stone);
			break;
		case materialStone:
		case materialOil:
		case materialPurified:
			auction.setType(EAuctionType.material);
			break;
		default:
			auction.setType(EAuctionType.other);
			break;
		}

		// 把新加入的物品加入内存 并排序
		AuctionManager.instance().add(auction);
		// 从背包删除物品
		Map<Integer, Cell> cellsMap = new HashMap<Integer, Cell>();
		if (type == 1) {
			online.getBag().removeByIndex(cellId, num, cellsMap, EItemCost.auction);
			BagMessage.sendBag(online, cellsMap, false);
		} else {
			online.getBagStone().removeByIndex(cellId, num, cellsMap, EItemCost.auction);
			// 刷新背包
			BagMessage.sendBagStone(online, cellsMap);
		}

		// 加入我的拍卖,刷新
		online.getAuctions().add(auction);

		// 把信息存入数据库
		this.getAuctionService().insert(auction);

		LogManager.auction(online.getId(), online.getUserId(), auction, Eauction.add.ordinal());
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_addAuction");
			message.write("result", result);
			if (result == 0) {
				reason = reason + "，您以" + auction.getBuyoutprice() + "的价格上架" + auction.getItem().getName();
			}
			message.write("reason", reason);
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
