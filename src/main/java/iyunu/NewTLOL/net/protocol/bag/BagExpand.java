package iyunu.NewTLOL.net.protocol.bag;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.bag.Bag;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 背包拓展
 * @author LuoSR
 * @date 2014年4月9日
 */
public class BagExpand extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "";

		int expandNum = msg.readInt("expandNum");

		int coin = expandNum * (1000 * (online.getBag().getSize() - Bag.DEFAULT_CELLS + 1)) + (expandNum * (expandNum - 1) * 1000) / 2;

		if (!RoleServer.costCoin(online, coin, EGold.bagExpand)) {
			result = 1;
			reason = "";
			SendMessage.refreshNoCoin(online, 2);
			return;
		}

		/** 扩展 **/
		online.getBag().expand(expandNum);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_bagExpand");
			message.write("result", result);
			message.write("reason", reason);
			message.write("sum", online.getBag().getSize());
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}