package iyunu.NewTLOL.net.protocol.auction;

import iyunu.NewTLOL.manager.AuctionManager;
import iyunu.NewTLOL.message.PartnerMessage;
import iyunu.NewTLOL.model.auction.Auction;
import iyunu.NewTLOL.model.partner.instance.Partner;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 进入拍卖行查看伙伴
 * 
 * @author SunHonglei
 * 
 */
public class ViewAuctionPartner extends TLOLMessageHandler {

	private long auctionId = 0;

	@Override
	public void handleReceived(LlpMessage msg) {
		auctionId = msg.readLong("auctionId");
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_viewAuctionPartner");

			Auction auction = AuctionManager.instance().getAuction(auctionId);
			Partner partner = null;
			if (auction != null) {
				partner = auction.getPartner();
			}
			LlpMessage msg = message.write("partner");
			PartnerMessage.packagePartner(msg, partner);

			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
