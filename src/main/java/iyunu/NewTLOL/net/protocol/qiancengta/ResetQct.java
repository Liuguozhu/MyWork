package iyunu.NewTLOL.net.protocol.qiancengta;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.vip.EVip;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * @function 重置千层塔
 * @author LuoSR
 * @date 2014年8月14日
 */
public class ResetQct extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "";

	@Override
	public void handleReceived(LlpMessage msg) {
		// ======数据重置======
		result = 0;
		reason = "重置成功";

		int type = msg.readInt("type");

		if (type == 0) {
			if (online.getNullResetNum() <= 0) {
				result = 1;
				reason = "已没有免费重置次数";
				return;
			}

			online.setNullResetNum(online.getNullResetNum() - 1);
			online.setCurrentFloorId(1);
		} else {
			if (online.getVip().isVip(EVip.common)) {
				result = 1;
				reason = "银两重置为vip功能";
				return;
			}

			if (online.getVip().getLevel().getQiancengtaAdd() - online.getMoneyResetNum() <= 0) {
				result = 1;
				reason = "已没有银两重置次数";
				return;
			}

			if (!RoleServer.costCoin(online, 25000, EGold.qiancengta)) {
				result = 1;
				reason = "";
				SendMessage.refreshNoCoin(online, 2);
				return;
			}

			online.setMoneyResetNum(online.getMoneyResetNum() + 1);
			online.setCurrentFloorId(1);
		}

	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_resetQct");
			message.write("result", result);
			message.write("reason", reason);

			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}

		if (result == 0) {
			SendMessage.sendCurrentFloorId(online);
		}
	}
}
