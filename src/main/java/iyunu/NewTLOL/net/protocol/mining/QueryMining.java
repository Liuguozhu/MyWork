package iyunu.NewTLOL.net.protocol.mining;

import iyunu.NewTLOL.json.MiningJson;
import iyunu.NewTLOL.manager.MiningManger;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.model.mining.Mining;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

public class QueryMining extends TLOLMessageHandler {

	private int result = 0;
	private String reason = "查询采矿成功";
	private int index = 0;
	Mining mining = null;

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "查询采矿成功";
		index = msg.readInt("index");
		if (index < 1 || index > MiningJson.instance().getMining().size()) {
			result = 1;
			reason = "查询采矿错误！";
			return;
		}
		mining = MiningManger.instance().getMiningMap().get(index);
		if (mining == null) {
			result = 1;
			reason = "查询采矿信息错误！";
			return;
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_queryMining");
			message.write("result", result);
			if (result == 0) {
				int flag = 0;// 0无人 1其它人2自己 ---------------对应 0占领 1抢夺 2 收取
				if (mining.getFlag() == 1) {
					if (mining.getRole().getId() == online.getId()) {
						flag = 2;
					} else {
						flag = 1;
					}
				}
				message.write("flag", flag);
				if (mining.getRole() != null) {
					message.write("id", mining.getRole().getId());
					message.write("name", RoleManager.getNameById(mining.getRole().getId()));
					message.write("gangName", GangManager.instance().getGangName(mining.getRole().getId()));
				} else {
					message.write("id", 0l);
					message.write("name", "");
					message.write("gangName", "");
				}
				message.write("type", mining.getType());

				if (flag == 0) {
					message.write("rec", mining.getValue());
				} else {
					message.write("rec", MiningManger.instance().countRec(mining.getStartTime(), mining.getValue()));
					message.write("restTime", MiningManger.instance().getRestTime(mining.getStartTime()));
				}

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
