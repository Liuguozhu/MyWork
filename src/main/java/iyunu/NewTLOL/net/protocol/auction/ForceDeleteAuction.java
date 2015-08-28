package iyunu.NewTLOL.net.protocol.auction;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.enumeration.common.Eauction;
import iyunu.NewTLOL.manager.AuctionManager;
import iyunu.NewTLOL.model.auction.Auction;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.mail.MailServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.LinkedHashMap;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class ForceDeleteAuction extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "已成功下架物品";
	private long auctionid = 0;
	private LinkedHashMap<Long, Auction> auctionMap = null;
	private double tax = 0.05;
	private Auction auction = null;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "已成功下架物品";
		auction = null;
		auctionid = msg.readLong("auctionid");
		if (auctionid <= 0) {
			result = 1;
			reason = "操作失败,请选择物品";
			return;
		}

		auctionMap = AuctionManager.instance().getAuctionMap();
		auction = auctionMap.get(auctionid);
		if (!auctionMap.keySet().contains(auctionid)) {
			result = 1;
			reason = "物品不存在";
			return;
		}

		if (auction.getOwnerId() != online.getId()) {
			result = 1;
			reason = "失败，物品不属于你。";
			return;
		}

		// 扣除强制下架的手续费
		int costTax = (int) Math.ceil(auction.getBuyoutprice() * tax);
		if (online.getCoin() < costTax) {
			result = 1;
			reason = "取出要扣3%的手续费，您的余额不足，点击确认购买银两";
			return;
		}

		RoleServer.costCoin(online, costTax, EGold.forceDeleteAuction);
		LogManager.auction(online.getId(), online.getUserId(), auction, Eauction.forceDelete.ordinal());
		switch (auction.getType()) {
		case money:
			// 给买家加元宝
			MailServer.send(online.getId(), "下架成功！", "本次手续费为" + costTax + "银两，请查收下架的物品！", null, 0, 0, 0, auction.getMoney(), 0, null, "拍卖行");
			break;
		case partner:
			MailServer.send(online.getId(), "下架成功！", "本次手续费为" + costTax + "银两，请查收下架的物品！", null, 0, 0, 0, 0, 0, auction.getPartner(), "拍卖行");
			break;
		default:
			MailServer.send(online.getId(), "下架成功！", "本次手续费为" + costTax + "银两，请查收下架的物品！", auction.getItem(), auction.getNum(), 0, 0, 0, 0, null, "拍卖行");
			break;
		}

		// 从数据库删除
		this.getAuctionService().delete(auction);
		// 从内存中删除
		AuctionManager.instance().delete(auction, online);

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_forceDeleteAuction");
			message.write("result", result);
			if (result == 0) {
				reason = reason + "，您花费了" + (int) Math.ceil(auction.getBuyoutprice() * tax) + "手续费";
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
