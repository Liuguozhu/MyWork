package iyunu.NewTLOL.net.protocol.auction;

import iyunu.NewTLOL.enumeration.common.EMoney;
import iyunu.NewTLOL.enumeration.common.Eauction;
import iyunu.NewTLOL.manager.AuctionManager;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.UidManager;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.auction.Auction;
import iyunu.NewTLOL.model.auction.EAuctionType;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;
import iyunu.NewTLOL.util.log.LogManager;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class AddAuctionMoney extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "上架元宝成功";
	private int money = 0;
	private int buyoutprice = 0;
	private Auction auction = null;

	// private double tax = 0.03;// 手续费率

	@Override
	public void handleReceived(LlpMessage msg) {
		// System.out.println("进入上架元宝");
		result = 0;
		reason = "上架元宝成功";
		money = 0;
		buyoutprice = 0;
		auction = new Auction();

		money = msg.readInt("money");
		buyoutprice = msg.readInt("buyoutprice");

		if (buyoutprice > RoleManager.MAX_COIN) {
			result = 1;
			reason = "上架物品失败,不可超过" + RoleManager.MAX_COIN + "银两";
			return;
		}
		if (money <= 0) {
			result = 1;
			reason = "数字不合法";
			return;
		}
		if (online.getMoney() <= 0 || money > online.getMoney()) {
			result = 1;
			reason = "元宝不足";
			return;
		}

		if (buyoutprice <= 0) {
			result = 1;
			reason = "上架元宝失败,请输入要拍卖的价格";
			return;
		}

		// if (online.getGold() < Math.ceil(buyoutprice * tax)) {
		// result = 1;
		// reason = "您的费用不足";
		// return;
		// }
		if (online.getAuctions().size() >= 4) {
			result = 1;
			reason = "您上架的物品已超过了最大数量：" + 4 + "个";
			return;
		}

		// 扣除税
		// RoleServer.costGold(online, (int) Math.ceil(buyoutprice * tax),
		// EGold.addAuctionMoney);
		// 对象设置属性，准备插入数据库
//		long auctionId = redisCenter.getAuctionId();// 取得拍卖ID
		auction.setId(UidManager.instance().auctionUid());
		auction.setOwnerId(online.getId());
		auction.setOwnerName(online.getNick());
		auction.setMoney(money);
		auction.setType(EAuctionType.valueOf("money"));
		auction.setBuyoutprice(buyoutprice);
		auction.setSrv(ServerManager.instance().getServer());
		auction.setTimeout(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
		auction.setTitle("元宝*" + auction.getMoney());

		// 把信息存入数据库
		this.getAuctionService().insert(auction);
		// 减少元宝
		RoleServer.costMoney(online, money, EMoney.auction);
		SendMessage.sendMoney(online);
		// 加入我的拍卖,刷新
		online.getAuctions().add(auction);
		// 把新加入的物品加入内存
		AuctionManager.instance().add(auction);
		// AuctionManager.instance.sort();
		LogManager.auction(online.getId(), online.getUserId(), auction, Eauction.addMoney.ordinal());
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_addAuction");
			message.write("result", result);
			if (result == 0) {
				reason = reason + "，您以" + buyoutprice + "的价格上架" + auction.getMoney() + "元宝";
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
