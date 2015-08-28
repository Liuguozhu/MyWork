package iyunu.NewTLOL.net.protocol.mining;

import iyunu.NewTLOL.manager.MiningManger;
import iyunu.NewTLOL.manager.RoleManager;
import iyunu.NewTLOL.model.mining.Mining;
import iyunu.NewTLOL.model.mining.MiningPage;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 查询矿区列表
 * 
 * @author fenghaiyu
 *
 */
public class QueryMiningList extends TLOLMessageHandler {
	private int page;
	private int result = 0;
	private String reason = "请求矿区列表成功";

	@Override
	public void handleReceived(LlpMessage msg) {
		result = 0;
		reason = "请求矿区列表成功";
		page = msg.readInt("page");

		if (!MiningManger.instance().ifPage(page)) {
			result = 1;
			reason = "请求矿区列表页数发生错误！";
			return;
		}
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {
			message = LlpJava.instance().getMessage("s_queryMiningList");
			message.write("result", result);
			if (result == 0) {
				MiningPage miningPage = MiningManger.instance().getPageMining(page);
				List<Mining> list = miningPage.getMiningList();

				for (int i = 0; i < list.size(); i++) {
					LlpMessage msg = message.write("minings");
					Mining mining = list.get(i);
					msg.write("index", mining.getIndex());
					msg.write("type", mining.getType());
					msg.write("flag", mining.getFlag());
					if (mining.getRole() != null) {
						msg.write("name", RoleManager.getNameById(mining.getRole().getId()));
						msg.write("roleId", mining.getRole().getId());
					} else {
						msg.write("name", "");
						msg.write("roleId", 0l);
					}

					msg.write("rate", MiningManger.instance().countRate(mining.getStartTime()));
				}
				message.write("page", miningPage.getPage());
				message.write("totalPage", miningPage.getTotalPage());
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
