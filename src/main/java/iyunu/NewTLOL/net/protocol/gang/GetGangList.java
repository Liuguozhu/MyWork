package iyunu.NewTLOL.net.protocol.gang;

import iyunu.NewTLOL.manager.gang.GangManager;
import iyunu.NewTLOL.model.gang.Gang;
import iyunu.NewTLOL.net.TLOLMessageHandler;

import java.util.List;

import com.liteProto.LlpJava;
import com.liteProto.LlpMessage;

/**
 * 请求要申请加入的帮派的列表
 * 
 * @author fenghaiyu
 * 
 */
public class GetGangList extends TLOLMessageHandler {
	public static final int page_max = 10;
	private int page = 1;
	private int result = 0;
	private String reason = "操作成功！";

	@Override
	public void handleReceived(LlpMessage msg) {
		page = 1;
		page = msg.readInt("page");
	}

	@Override
	public void handleReply() throws Exception {
		LlpMessage message = null;
		try {

			message = LlpJava.instance().getMessage("s_gangList");
			message.write("result", result);
			message.write("reason", reason);
			if (result == 0) {
				int totalpage = 0;
				GangManager.instance().sortGangs();
				List<Gang> gangList = GangManager.instance().getOrderList();

				if (gangList.size() > 0) {
					int start = (page - 1) * page_max + 1;
					int end = start + page_max > gangList.size() ? gangList.size() + 1 : start + page_max;
					totalpage = (gangList.size() - 1) / page_max + 1;
					gangList = gangList.subList(start - 1, end - 1);
				}

				for (Gang gang : gangList) {
					LlpMessage msg = message.write("gangs");
					msg.write("gangId", gang.getId());
					msg.write("name", gang.getName());
					msg.write("level", gang.getLevel());
					msg.write("size", gang.getSize());
					msg.write("num", gang.getMembers().size());
					msg.write("leaderName", gang.getLeaderName());
					msg.write("asked", online.getAskGang().contains(gang.getId()) ? 1 : 0);
				}
				if (totalpage <= 0) {
					totalpage = 1;
				}
				message.write("totalpage", totalpage);
				message.write("page", page);
				message.write("pageMax", page_max);
			}

			channel.write(message);
		} finally {
			if (message != null) {
				message.destory();
			}
		}
	}
}
