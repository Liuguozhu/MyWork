package iyunu.NewTLOL.net.protocol.warehouse;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 取出仓库银两
 * @author fhy
 * @date 2014年10月24日
 */
public class GetWarehouseCoin extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "取出成功";
		int coin = msg.readInt("coin");
		if (coin <= 0) {
			result = 1;
			reason = "填入的数目不正确!";
			return;
		}
		if (online.getWarehouseCoin() < coin) {
			result = 1;
			reason = "仓库没有足够的银两!";
			return;
		}
		if (online.getCoin() + coin > RoleManager.MAX_COIN) {
			result = 1;
			reason = "超出可携带的银两上限!";
			return;
		}
		RoleServer.addCoin(online, coin, EGold.wareHouseCoin);
		online.setWarehouseCoin(online.getWarehouseCoin() - coin);
		SendMessage.refreshWareHouseCoin(online);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_addWarehouse");
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