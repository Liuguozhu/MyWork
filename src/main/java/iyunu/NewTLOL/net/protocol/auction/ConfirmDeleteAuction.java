package iyunu.NewTLOL.net.protocol.auction;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.enumeration.common.Eauction;
import iyunu.NewTLOL.manager.AuctionManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.auction.Auction;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.mail.MailServer;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.LinkedHashMap;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class ConfirmDeleteAuction extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "已成功拍下物品!";
	private long auctionId = 0;
	private LinkedHashMap<Long, Auction> auctionMap = null;
	private double tax = 0.05;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "已成功拍下物品!";
		try {

			auctionId = msg.readLong("auctionId");
			auctionMap = AuctionManager.instance().getAuctionMap();

			if (auctionId <= 0) {
				result = 1;
				reason = "操作失败,请选择物品!";
				return;
			}

			if (!auctionMap.containsKey(auctionId)) {
				result = 1;
				reason = "物品不存在!";
				return;
			}

			Auction auction = auctionMap.get(auctionId);

			auction.setSrv(ServerManager.instance().getServer());
			if (online.getCoin() < auction.getBuyoutprice()) {
				result = 1;
				reason = "";
				SendMessage.refreshNoCoin(online, 2);
				return;
			}

			if (auction.getOwnerId() == online.getId()) {
				result = 1;
				reason = "不可拍下自己的物品，请进入我的拍卖进行下架!";
				return;
			}
			// 扣钱
			// RoleServer.costGold(online, auction.getBuyoutprice(),
			// EGold.confirmDeleteAuction);
			RoleServer.costCoin(online, auction.getBuyoutprice(), EGold.confirmDeleteAuction);
			LogManager.auction(online.getId(), online.getUserId(), auction, Eauction.confirm.ordinal());
			switch (auction.getType()) {
			case money:
				MailServer.send(online.getId(), "拍下成功！", "请查收！", null, 0, 0, 0, auction.getMoney(), 0, null, "拍卖行");
				break;
			case partner:
				MailServer.send(online.getId(), "拍下成功！", "请查收！", null, 0, 0, 0, 0, 0, auction.getPartner(), "拍卖行");
				break;
			default:
				MailServer.send(online.getId(), "拍下成功！", "请查收！", auction.getItem(), auction.getNum(), 0, 0, 0, 0, null, "拍卖行");
				break;
			}

			// 给拍卖者发送报酬邮件
			long ownerId = auction.getOwnerId();
			if (GangManager.instance().isChampion(online)) {
				tax = 0.02;
			}
			int taxMoney = (int) (auction.getBuyoutprice() * (1 - tax));

			MailServer.send(ownerId, "物品被拍下！", " 您的 " + auction.getTitle() + " 被拍下，本次手续费为" + (int) Math.ceil((auction.getBuyoutprice() * tax)) + "银两！", null, 0, taxMoney, 0, 0, null);

			// 从数据库删除
			this.getAuctionService().delete(auction);
			// 从内存中删除
			AuctionManager.instance().delete(auction, ServerManager.instance().getOnlinePlayer(auction.getOwnerId()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_confirmDeleteAuction");
			message.write("result", result);
			message.write("reason", reason);

			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
