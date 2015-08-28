package iyunu.NewTLOL.net.protocol.auction;

import iyunu.NewTLOL.enumeration.EColor;
import iyunu.NewTLOL.manager.AuctionManager;
import iyunu.NewTLOL.model.auction.Auction;
import iyunu.NewTLOL.model.auction.EAuctionType;
import iyunu.NewTLOL.model.item.instance.Equip;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.util.Time;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 我的拍卖
 * 
 * @author SunHonglei
 * 
 */
public class QueryMyAuction extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_myAuction");
			long now = System.currentTimeMillis();
			for (Auction auction : online.getAuctions()) {
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
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
