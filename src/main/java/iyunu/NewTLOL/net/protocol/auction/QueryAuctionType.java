package iyunu.NewTLOL.net.protocol.auction;

import iyunu.NewTLOL.model.auction.EAuctionType;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 拍卖类型
 * 
 * @author SunHonglei
 * 
 */
public class QueryAuctionType extends TLOLMessageHandler {

	@Override
	public void handleReceived(LlpMessage msg) {
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_auctionType");
			for (EAuctionType auctionType : EAuctionType.values()) {
				LlpMessage msg = message.write("auctionTypes");
				msg.write("name", auctionType.getName());
				msg.write("type", auctionType.ordinal());
			}
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
