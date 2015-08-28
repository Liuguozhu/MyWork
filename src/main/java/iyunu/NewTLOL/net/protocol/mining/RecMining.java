package iyunu.NewTLOL.net.protocol.mining;

import iyunu.NewTLOL.enumeration.common.EGold;
import iyunu.NewTLOL.json.MiningJson;
import iyunu.NewTLOL.manager.MiningManger;
import iyunu.NewTLOL.message.SendMessage;
import iyunu.NewTLOL.model.mining.Mining;
import iyunu.NewTLOL.net.TLOLMessageHandler;
import iyunu.NewTLOL.server.role.RoleServer;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class RecMining extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "收取矿区成功";
	private int index = 0;
	Mining mining = null;
	private int page = 0;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "收取矿区成功";
		index = msg.readInt("index");
		page = msg.readInt("page");
		SendMessage.refreshMiningList(online, page);
		if (index < 1 || index > MiningJson.instance().getMining().size()) {
			result = 1;
			reason = "收取矿区错误！";
			return;
		}
		mining = MiningManger.instance().getMiningMap().get(index);
		if (mining == null) {
			result = 1;
			reason = "收取矿区信息错误！";
			return;
		}

		if (!MiningManger.instance().ifPageIndex(page, index)) {
			result = 1;
			reason = "收矿页数信息发生错误！";
			return;
		}
		if (mining.getFlag() == 0) {
			result = 1;
			reason = "本矿区还没有被占领，可以刷新后占领！";
			return;
		}
		if (mining.getRole() != null) {
			if (mining.getRole().getId() != online.getId()) {
				result = 1;
				reason = "这不是你的矿！";
				return;
			}
		}

		int totalValue = MiningManger.instance().countRec(mining.getStartTime(), mining.getValue());
		if (totalValue <= 0) {
			reason = "一分钱没有，做事要持之以恒啊！";
		}
		
		totalValue += totalValue * online.getVip().getLevel().getMiningGoldAdd();
		
		RoleServer.addGold(online, totalValue, EGold.mining);
		mining.setFlag(0);
		mining.setRole(null);
		mining.setStartTime(0);
		SendMessage.refreshMiningList(online, page);
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_recMining");
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
