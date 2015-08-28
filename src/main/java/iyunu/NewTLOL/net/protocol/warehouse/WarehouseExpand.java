package iyunu.NewTLOL.net.protocol.warehouse;

import iyunu.NewTLOL.enumeration.common.EMoney;
import iyunu.NewTLOL.manager.WarehouseManager;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 仓库拓展
 * @author LuoSR
 * @date 2014年4月9日
 */
public class WarehouseExpand extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "";

		int expandNum = msg.readInt("expandNum");

		if (!RoleServer.costMoney(online, WarehouseManager.USEMONEY * expandNum, EMoney.warehouseExpand)) {
			result = 1;
			reason = "";
			SendMessage.refreshNoCoin(online, 3);
			return;
		}

		/** 扩展 **/
		online.getWarehouse().expand(expandNum);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_bagExpand");
			message.write("result", result);
			message.write("reason", reason);
			message.write("sum", online.getWarehouse().getSize());
			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}