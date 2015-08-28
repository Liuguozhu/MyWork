package iyunu.NewTLOL.net.protocol.auction;

import iyunu.NewTLOL.enumeration.common.Eauction;
import iyunu.NewTLOL.manager.AuctionManager;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.manager.ServerManager;
import iyunu.NewTLOL.manager.UidManager;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.model.auction.Auction;
import iyunu.NewTLOL.model.auction.EAuctionType;
import iyunu.NewTLOL.model.partner.EpartnerOperate;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.log.LogManager;

import java.util.ArrayList;
import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class AddAuctionPartner extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "上架宠物成功";
	private long partnerId = 0;
	private int buyoutprice = 0;
	private Auction auction = null;

	// private double tax = 0.03;// 手续费率

	@Override
	public void handleReceived(LlpMessage msg) {
		// System.out.println("进入上架宠物");
		result = 0;
		reason = "上架宠物成功";
		partnerId = 0;
		buyoutprice = 0;
		partnerId = msg.readLong("partnerId");
		buyoutprice = msg.readInt("buyoutprice");
		auction = new Auction();
		if (partnerId <= 0) {
			result = 1;
			reason = "上架宠物失败,请放入宠物";
			return;
		}

		if (buyoutprice > RoleManager.MAX_COIN) {
			result = 1;
			reason = "上架物品失败,不可超过" + RoleManager.MAX_COIN + "银两";
			return;
		}
		if (buyoutprice <= 0) {
			result = 1;
			reason = "上架宠物失败,请输入要拍卖的价格";
			return;
		}

		// if (online.getGold() < Math.ceil(buyoutprice * tax)) {
		// result = 1;
		// reason = "您的费用不足";
		// return;
		// }
		if (online.getAuctions().size() >= 4) {
			result = 1;
			reason = "您上架的物品已超过了最大数量 4 个";
			return;
		}
		// 扣除税
		// RoleServer.costGold(online, (int) Math.ceil(buyoutprice * tax),
		// EGold.addAuctionPartner);
		// 对象设置属性，准备插入数据库

		Partner partner = online.findPartner(partnerId);
		if (partner == null) {
			result = 1;
			reason = "操作失败，数据错误！";
			return;
		}

		if (partner.getIsBind() == 1) {
			result = 1;
			reason = "绑定伙伴不能拍卖";
			return;
		}
		// long auctionId = redisCenter.getAuctionId();// 取得拍卖ID
		auction.setId(UidManager.instance().auctionUid());
		auction.setOwnerId(online.getId());
		auction.setOwnerName(online.getNick());
		auction.setPartnerId(partnerId);
		auction.setPartner(partner);

		auction.setBuyoutprice(buyoutprice);
		auction.setSrv(ServerManager.instance().getServer());
		auction.setTimeout(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
		auction.setTitle(auction.getPartner().getNick());
		auction.setType(EAuctionType.valueOf("partner"));

		// 把信息存入数据库
		this.getAuctionService().insert(auction);
		// 删除宠物
		online.getPartnerMap().remove(partnerId);
		// 加入我的拍卖,刷新
		online.getAuctions().add(auction);
		// 把新加入的物品加入内存//并排序
		AuctionManager.instance().add(auction);
		// AuctionManager.instance.sort();
		List<Partner> partnerList = new ArrayList<>();
		partner.setOperateFlag(EpartnerOperate.delete);
		partnerList.add(partner);
		PartnerMessage.sendPartners(online, partnerList);
		// PartnerMessage.sendPartners(online);
		LogManager.auction(online.getId(), online.getUserId(), auction, Eauction.addPartner.ordinal());
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_addAuction");
			message.write("result", result);
			if (result == 0) {
				reason = reason + "，您以" + buyoutprice + "的价格上架" + auction.getPartner().getNick();
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
